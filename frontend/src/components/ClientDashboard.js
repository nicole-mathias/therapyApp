import React, { useState, useEffect } from 'react';
import axios from '../utils/axiosConfig';
import TherapyChat from './TherapyChat';
import PaymentInfo from './PaymentInfo';
import './ClientDashboard.css';

const ClientDashboard = ({ user, client }) => {
    const [activeTab, setActiveTab] = useState('daily-log');
    const [dailyLog, setDailyLog] = useState({
        moodScore: 5,
        moodDescription: '',
        activities: '',
        thoughts: '',
        sleepHours: 8,
        medicationTaken: false,
        therapySession: false
    });
    const [logs, setLogs] = useState([]);
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState('');

    useEffect(() => {
        if (client) {
            fetchDailyLogs();
        }
    }, [client]);

    const fetchDailyLogs = async () => {
        try {
            if (!client) {
                console.log('No client record found, skipping log fetch');
                return;
            }
            const response = await axios.get(`/therapy/daily-logs/client/${client.id}`);
            setLogs(response.data);
        } catch (error) {
            console.error('Error fetching logs:', error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value, type, checked } = e.target;
        setDailyLog(prev => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value
        }));
    };

    const handleSubmitLog = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
            // For client users, we need to find or create a client record
            let clientId = null;
            
            if (client) {
                clientId = client.id;
            } else {
                // If no client record exists, we need to create one
                // For now, let's show an error message
                setMessage('Error: No client record found. Please contact your therapist.');
                setLoading(false);
                return;
            }

            const logData = {
                ...dailyLog,
                clientId: clientId,
                logDate: new Date().toISOString().split('T')[0]
            };

            const response = await axios.post('/therapy/daily-logs', logData);
            
            if (response.data.success) {
                setMessage('Daily log saved successfully!');
                setDailyLog({
                    moodScore: 5,
                    moodDescription: '',
                    activities: '',
                    thoughts: '',
                    sleepHours: 8,
                    medicationTaken: false,
                    therapySession: false
                });
                fetchDailyLogs();
            }
        } catch (error) {
            setMessage('Error saving log. Please try again.');
            console.error('Error saving log:', error);
        } finally {
            setLoading(false);
            setTimeout(() => setMessage(''), 3000);
        }
    };

    const getMoodEmoji = (score) => {
        if (score >= 8) return '😊';
        if (score >= 6) return '🙂';
        if (score >= 4) return '😐';
        if (score >= 2) return '😔';
        return '😢';
    };

    const getMoodColor = (score) => {
        if (score >= 8) return '#4CAF50';
        if (score >= 6) return '#8BC34A';
        if (score >= 4) return '#FFC107';
        if (score >= 2) return '#FF9800';
        return '#F44336';
    };

    return (
        <div className="client-dashboard">
            <div className="dashboard-header">
                <h1>Welcome, {user.firstName}!</h1>
                <p>Track your daily progress and connect with your therapy</p>
            </div>

            <div className="dashboard-tabs">
                <button 
                    className={`tab ${activeTab === 'daily-log' ? 'active' : ''}`}
                    onClick={() => setActiveTab('daily-log')}
                >
                    📝 Daily Log
                </button>
                <button 
                    className={`tab ${activeTab === 'history' ? 'active' : ''}`}
                    onClick={() => setActiveTab('history')}
                >
                    📊 History
                </button>
                <button 
                    className={`tab ${activeTab === 'chat' ? 'active' : ''}`}
                    onClick={() => setActiveTab('chat')}
                >
                    💬 Therapy Chat
                </button>
                <button 
                    className={`tab ${activeTab === 'payment' ? 'active' : ''}`}
                    onClick={() => setActiveTab('payment')}
                >
                    💰 Payment Info
                </button>
            </div>

            {message && (
                <div className={`message ${message.includes('Error') ? 'error' : 'success'}`}>
                    {message}
                </div>
            )}

            {activeTab === 'daily-log' && (
                <div className="daily-log-section">
                    <h2>Today's Log</h2>
                    {!client && (
                        <div className="error-message">
                            <p>⚠️ No client record found. Please contact your therapist to set up your account.</p>
                        </div>
                    )}
                    <form onSubmit={handleSubmitLog} className="log-form" style={{ opacity: client ? 1 : 0.5 }}>
                        <div className="mood-section">
                            <h3>How are you feeling today?</h3>
                            <div className="mood-slider">
                                <label>Mood Score: {dailyLog.moodScore}/10 {getMoodEmoji(dailyLog.moodScore)}</label>
                                <input
                                    type="range"
                                    name="moodScore"
                                    min="1"
                                    max="10"
                                    value={dailyLog.moodScore}
                                    onChange={handleInputChange}
                                    style={{ accentColor: getMoodColor(dailyLog.moodScore) }}
                                />
                                <div className="mood-labels">
                                    <span>Very Low</span>
                                    <span>Very High</span>
                                </div>
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-group">
                                <label>Mood Description:</label>
                                <textarea
                                    name="moodDescription"
                                    value={dailyLog.moodDescription}
                                    onChange={handleInputChange}
                                    placeholder="Describe how you're feeling..."
                                    rows="3"
                                />
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-group">
                                <label>Activities Today:</label>
                                <textarea
                                    name="activities"
                                    value={dailyLog.activities}
                                    onChange={handleInputChange}
                                    placeholder="What did you do today?"
                                    rows="3"
                                />
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-group">
                                <label>Thoughts & Feelings:</label>
                                <textarea
                                    name="thoughts"
                                    value={dailyLog.thoughts}
                                    onChange={handleInputChange}
                                    placeholder="Share your thoughts, worries, or achievements..."
                                    rows="4"
                                />
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-group">
                                <label>Sleep Hours:</label>
                                <input
                                    type="number"
                                    name="sleepHours"
                                    value={dailyLog.sleepHours}
                                    onChange={handleInputChange}
                                    min="0"
                                    max="24"
                                    step="0.5"
                                />
                            </div>
                        </div>

                        <div className="checkbox-group">
                            <label className="checkbox-label">
                                <input
                                    type="checkbox"
                                    name="medicationTaken"
                                    checked={dailyLog.medicationTaken}
                                    onChange={handleInputChange}
                                />
                                <span>I took my medication today</span>
                            </label>

                            <label className="checkbox-label">
                                <input
                                    type="checkbox"
                                    name="therapySession"
                                    checked={dailyLog.therapySession}
                                    onChange={handleInputChange}
                                />
                                <span>I had a therapy session today</span>
                            </label>
                        </div>

                        <button 
                            type="submit" 
                            className="submit-btn"
                            disabled={loading || !client}
                        >
                            {loading ? 'Saving...' : !client ? 'No Client Record' : 'Save Daily Log'}
                        </button>
                    </form>
                </div>
            )}

            {activeTab === 'history' && (
                <div className="history-section">
                    <h2>Your Log History</h2>
                    {!client ? (
                        <div className="error-message">
                            <p>⚠️ No client record found. Please contact your therapist to set up your account.</p>
                        </div>
                    ) : (
                        <div className="logs-grid">
                            {logs.map(log => (
                            <div key={log.id} className="log-card">
                                <div className="log-header">
                                    <span className="log-date">
                                        {new Date(log.logDate).toLocaleDateString()}
                                    </span>
                                    <span className="mood-score">
                                        {log.moodScore}/10 {getMoodEmoji(log.moodScore)}
                                    </span>
                                </div>
                                
                                {log.moodDescription && (
                                    <p className="mood-desc">{log.moodDescription}</p>
                                )}
                                
                                {log.activities && (
                                    <p className="activities"><strong>Activities:</strong> {log.activities}</p>
                                )}
                                
                                {log.thoughts && (
                                    <p className="thoughts"><strong>Thoughts:</strong> {log.thoughts}</p>
                                )}
                                
                                <div className="log-stats">
                                    <span>Sleep: {log.sleepHours}h</span>
                                    {log.medicationTaken && <span className="medication">💊</span>}
                                    {log.therapySession && <span className="therapy">🛋️</span>}
                                </div>
                            </div>
                        ))}
                        </div>
                    )}
                </div>
            )}

            {activeTab === 'chat' && (
                <div className="chat-section">
                    {!client ? (
                        <div className="error-message">
                            <p>⚠️ No client record found. Please contact your therapist to set up your account.</p>
                        </div>
                    ) : (
                        <TherapyChat client={client} />
                    )}
                </div>
            )}

            {activeTab === 'payment' && (
                <div className="payment-section">
                    <PaymentInfo client={client} />
                </div>
            )}
        </div>
    );
};

export default ClientDashboard; 