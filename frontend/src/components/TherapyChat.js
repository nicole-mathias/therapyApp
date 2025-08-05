import React, { useState, useEffect, useRef } from 'react';
import axios from '../utils/axiosConfig';
import './TherapyChat.css';

const TherapyChat = ({ client }) => {
    const clientId = client?.id;
    const clientName = client?.name;
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState('');
    const [sessionId, setSessionId] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [crisisAlert, setCrisisAlert] = useState(null);
    const [showEmergencyModal, setShowEmergencyModal] = useState(false);
    const messagesEndRef = useRef(null);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    };

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    useEffect(() => {
        if (clientId && !sessionId) {
            startNewSession();
        }
    }, [clientId, sessionId]);

    const startNewSession = async () => {
        try {
            // Check if we have a stored session for this client
            const storedSession = localStorage.getItem(`chat_session_${clientId}`);
            const storedMessages = localStorage.getItem(`chat_messages_${clientId}`);
            
            if (storedSession && storedMessages) {
                // Restore existing session
                setSessionId(storedSession);
                setMessages(JSON.parse(storedMessages));
            } else {
                // Start new session
                const response = await axios.post(`/therapy/chat/start-session/${clientId}`);
                setSessionId(response.data.sessionId);
                
                // Add welcome message
                const welcomeMessage = {
                    id: 'welcome',
                    messageText: response.data.welcomeMessage,
                    isFromAI: true,
                    timestamp: new Date(),
                    crisisDetected: false
                };
                setMessages([welcomeMessage]);
                
                // Store session and messages
                localStorage.setItem(`chat_session_${clientId}`, response.data.sessionId);
                localStorage.setItem(`chat_messages_${clientId}`, JSON.stringify([welcomeMessage]));
            }
        } catch (error) {
            console.error('Error starting session:', error);
        }
    };

    const sendMessage = async () => {
        if (!newMessage.trim() || !sessionId) return;

        const userMessage = {
            id: Date.now(),
            messageText: newMessage,
            isFromAI: false,
            timestamp: new Date(),
            crisisDetected: false
        };

        const updatedMessages = [...messages, userMessage];
        setMessages(updatedMessages);
        setNewMessage('');
        setIsLoading(true);

        try {
            const response = await axios.post('/therapy/chat/send', {
                clientId: clientId,
                message: newMessage,
                sessionId: sessionId
            });

            const aiMessage = {
                id: Date.now() + 1,
                messageText: response.data.message,
                isFromAI: true,
                timestamp: new Date(),
                crisisDetected: response.data.crisisDetected,
                crisisLevel: response.data.crisisLevel,
                crisisType: response.data.crisisType
            };

            const finalMessages = [...updatedMessages, aiMessage];
            setMessages(finalMessages);
            
            // Persist updated messages
            localStorage.setItem(`chat_messages_${clientId}`, JSON.stringify(finalMessages));

            // Check for crisis
            if (response.data.crisisDetected && response.data.crisisLevel >= 3) {
                setCrisisAlert({
                    level: response.data.crisisLevel,
                    type: response.data.crisisType,
                    message: response.data.message
                });
                setShowEmergencyModal(true);
            }

        } catch (error) {
            console.error('Error sending message:', error);
        } finally {
            setIsLoading(false);
        }
    };

    const handleKeyPress = (e) => {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            sendMessage();
        }
    };

    const clearChat = () => {
        localStorage.removeItem(`chat_session_${clientId}`);
        localStorage.removeItem(`chat_messages_${clientId}`);
        setSessionId(null);
        setMessages([]);
        startNewSession();
    };

    const callEmergencyContact = async () => {
        try {
            await axios.post(`/therapy/crisis/emergency-contact/${crisisAlert?.id}`);
            alert('Emergency contact has been notified');
        } catch (error) {
            console.error('Error calling emergency contact:', error);
        }
    };

    const callEmergencyServices = async () => {
        try {
            await axios.post(`/therapy/crisis/emergency-services/${crisisAlert?.id}`);
            alert('Emergency services have been contacted');
        } catch (error) {
            console.error('Error calling emergency services:', error);
        }
    };

    const getCrisisLevelColor = (level) => {
        switch (level) {
            case 1: return '#ffd700';
            case 2: return '#ffa500';
            case 3: return '#ff8c00';
            case 4: return '#ff4500';
            case 5: return '#ff0000';
            default: return 'transparent';
        }
    };

    return (
        <div className="therapy-chat-container">
            <div className="chat-header">
                <h2>Therapy Session - {clientName}</h2>
                <div className="header-actions">
                    <div className="session-info">
                        Session ID: {sessionId?.substring(0, 8)}...
                    </div>
                    <button 
                        onClick={clearChat}
                        className="clear-chat-btn"
                        title="Start a new chat session"
                    >
                        🔄 New Chat
                    </button>
                </div>
            </div>

            <div className="messages-container">
                {messages.map((message) => (
                    <div
                        key={message.id}
                        className={`message ${message.isFromAI ? 'ai-message' : 'user-message'}`}
                    >
                        <div className="message-content">
                            <div className="message-text">{message.messageText}</div>
                            <div className="message-time">
                                {new Date(message.timestamp).toLocaleTimeString()}
                            </div>
                            {message.crisisDetected && (
                                <div 
                                    className="crisis-indicator"
                                    style={{ backgroundColor: getCrisisLevelColor(message.crisisLevel) }}
                                >
                                    Crisis Level {message.crisisLevel}: {message.crisisType}
                                </div>
                            )}
                        </div>
                    </div>
                ))}
                {isLoading && (
                    <div className="message ai-message">
                        <div className="message-content">
                            <div className="typing-indicator">
                                <span></span>
                                <span></span>
                                <span></span>
                            </div>
                        </div>
                    </div>
                )}
                <div ref={messagesEndRef} />
            </div>

            <div className="input-container">
                <textarea
                    value={newMessage}
                    onChange={(e) => setNewMessage(e.target.value)}
                    onKeyPress={handleKeyPress}
                    placeholder="Type your message here..."
                    disabled={isLoading}
                    rows="3"
                />
                <button 
                    onClick={sendMessage} 
                    disabled={isLoading || !newMessage.trim()}
                    className="send-button"
                >
                    Send
                </button>
            </div>

            {/* Crisis Alert Modal */}
            {showEmergencyModal && crisisAlert && (
                <div className="modal-overlay">
                    <div className="crisis-modal">
                        <div className="crisis-header">
                            <h3>⚠️ Crisis Alert Detected</h3>
                        </div>
                        <div className="crisis-content">
                            <p><strong>Crisis Level:</strong> {crisisAlert.level}/5</p>
                            <p><strong>Type:</strong> {crisisAlert.type}</p>
                            <p><strong>Message:</strong> {crisisAlert.message}</p>
                            
                            <div className="emergency-actions">
                                <button 
                                    onClick={callEmergencyContact}
                                    className="emergency-button contact"
                                >
                                    📞 Call Emergency Contact
                                </button>
                                <button 
                                    onClick={callEmergencyServices}
                                    className="emergency-button services"
                                >
                                    🚨 Call Emergency Services
                                </button>
                            </div>
                            
                            <button 
                                onClick={() => setShowEmergencyModal(false)}
                                className="close-button"
                            >
                                Close Alert
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default TherapyChat; 