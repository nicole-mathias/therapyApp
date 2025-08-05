import React, { useState, useEffect } from 'react';
import axios from '../utils/axiosConfig';
import './CrisisDashboard.css';

const CrisisDashboard = () => {
    const [activeAlerts, setActiveAlerts] = useState([]);
    const [highRiskAlerts, setHighRiskAlerts] = useState([]);
    const [selectedAlert, setSelectedAlert] = useState(null);
    const [resolutionNotes, setResolutionNotes] = useState('');

    useEffect(() => {
        fetchAlerts();
        // Poll for new alerts every 30 seconds
        const interval = setInterval(fetchAlerts, 30000);
        return () => clearInterval(interval);
    }, []);

    const fetchAlerts = async () => {
        try {
            const [activeResponse, highRiskResponse] = await Promise.all([
                axios.get('/therapy/crisis/active'),
                axios.get('/therapy/crisis/high-risk')
            ]);
            
            setActiveAlerts(activeResponse.data);
            setHighRiskAlerts(highRiskResponse.data);
        } catch (error) {
            console.error('Error fetching alerts:', error);
        }
    };

    const resolveAlert = async (alertId) => {
        try {
            await axios.post(`/therapy/crisis/resolve/${alertId}`, {
                resolutionNotes: resolutionNotes
            });
            
            setResolutionNotes('');
            setSelectedAlert(null);
            fetchAlerts();
        } catch (error) {
            console.error('Error resolving alert:', error);
        }
    };

    const callEmergencyContact = async (alertId) => {
        try {
            await axios.post(`/therapy/crisis/emergency-contact/${alertId}`);
            alert('Emergency contact has been notified');
            fetchAlerts();
        } catch (error) {
            console.error('Error calling emergency contact:', error);
        }
    };

    const callEmergencyServices = async (alertId) => {
        try {
            await axios.post(`/therapy/crisis/emergency-services/${alertId}`);
            alert('Emergency services have been contacted');
            fetchAlerts();
        } catch (error) {
            console.error('Error calling emergency services:', error);
        }
    };

    const getAlertLevelColor = (level) => {
        switch (level) {
            case 1: return '#ffd700';
            case 2: return '#ffa500';
            case 3: return '#ff8c00';
            case 4: return '#ff4500';
            case 5: return '#ff0000';
            default: return '#ccc';
        }
    };

    const formatTimestamp = (timestamp) => {
        return new Date(timestamp).toLocaleString();
    };

    return (
        <div className="crisis-dashboard">
            <div className="dashboard-header">
                <h1>🚨 Crisis Alert Dashboard</h1>
                <div className="alert-summary">
                    <div className="summary-item">
                        <span className="count">{activeAlerts.length}</span>
                        <span className="label">Active Alerts</span>
                    </div>
                    <div className="summary-item high-risk">
                        <span className="count">{highRiskAlerts.length}</span>
                        <span className="label">High Risk</span>
                    </div>
                </div>
            </div>

            <div className="dashboard-content">
                <div className="alerts-section">
                    <h2>Active Crisis Alerts</h2>
                    {activeAlerts.length === 0 ? (
                        <div className="no-alerts">
                            <p>✅ No active crisis alerts</p>
                        </div>
                    ) : (
                        <div className="alerts-grid">
                            {activeAlerts.map((alert) => (
                                <div 
                                    key={alert.id} 
                                    className={`alert-card ${alert.alertLevel >= 4 ? 'high-risk' : ''}`}
                                    style={{ borderLeftColor: getAlertLevelColor(alert.alertLevel) }}
                                >
                                    <div className="alert-header">
                                        <div className="alert-level">
                                            Level {alert.alertLevel}
                                        </div>
                                        <div className="alert-type">
                                            {alert.alertType}
                                        </div>
                                    </div>
                                    
                                    <div className="alert-content">
                                        <p><strong>Client:</strong> {alert.client.name}</p>
                                        <p><strong>Description:</strong> {alert.description}</p>
                                        <p><strong>Time:</strong> {formatTimestamp(alert.timestamp)}</p>
                                    </div>

                                    <div className="alert-actions">
                                        <button 
                                            onClick={() => callEmergencyContact(alert.id)}
                                            className="action-button contact"
                                            disabled={alert.emergencyContactCalled}
                                        >
                                            {alert.emergencyContactCalled ? '✓ Contacted' : '📞 Contact'}
                                        </button>
                                        
                                        <button 
                                            onClick={() => callEmergencyServices(alert.id)}
                                            className="action-button services"
                                            disabled={alert.emergencyServicesCalled}
                                        >
                                            {alert.emergencyServicesCalled ? '✓ Called' : '🚨 Emergency'}
                                        </button>
                                        
                                        <button 
                                            onClick={() => setSelectedAlert(alert)}
                                            className="action-button resolve"
                                        >
                                            ✅ Resolve
                                        </button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            </div>

            {/* Resolution Modal */}
            {selectedAlert && (
                <div className="modal-overlay">
                    <div className="resolution-modal">
                        <div className="modal-header">
                            <h3>Resolve Crisis Alert</h3>
                            <button 
                                onClick={() => setSelectedAlert(null)}
                                className="close-button"
                            >
                                ×
                            </button>
                        </div>
                        
                        <div className="modal-content">
                            <p><strong>Client:</strong> {selectedAlert.client.name}</p>
                            <p><strong>Alert Level:</strong> {selectedAlert.alertLevel}</p>
                            <p><strong>Type:</strong> {selectedAlert.alertType}</p>
                            <p><strong>Description:</strong> {selectedAlert.description}</p>
                            
                            <div className="resolution-form">
                                <label>Resolution Notes:</label>
                                <textarea
                                    value={resolutionNotes}
                                    onChange={(e) => setResolutionNotes(e.target.value)}
                                    placeholder="Describe how the crisis was resolved..."
                                    rows="4"
                                />
                            </div>
                            
                            <div className="modal-actions">
                                <button 
                                    onClick={() => resolveAlert(selectedAlert.id)}
                                    className="resolve-button"
                                    disabled={!resolutionNotes.trim()}
                                >
                                    Resolve Alert
                                </button>
                                <button 
                                    onClick={() => setSelectedAlert(null)}
                                    className="cancel-button"
                                >
                                    Cancel
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default CrisisDashboard; 