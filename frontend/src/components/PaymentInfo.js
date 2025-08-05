import React, { useState, useEffect } from 'react';
import axios from '../utils/axiosConfig';
import './PaymentInfo.css';

const PaymentInfo = ({ client }) => {
    const [paymentInfo, setPaymentInfo] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (client && client.id) {
            fetchPaymentInfo();
        }
    }, [client]);

    const fetchPaymentInfo = async () => {
        try {
            setLoading(true);
            const response = await axios.get(`/therapy/management/client/${client.id}/payment`);
            
            if (response.data.success) {
                setPaymentInfo(response.data);
            }
        } catch (error) {
            console.error('Error fetching payment info:', error);
        } finally {
            setLoading(false);
        }
    };

    if (!client) {
        return (
            <div className="payment-info-container">
                <div className="no-client-message">
                    <h3>⚠️ No Client Record Found</h3>
                    <p>Please contact your therapist to set up your account.</p>
                </div>
            </div>
        );
    }

    if (loading) {
        return (
            <div className="payment-info-container">
                <div className="loading">Loading payment information...</div>
            </div>
        );
    }

    if (!paymentInfo) {
        return (
            <div className="payment-info-container">
                <div className="no-payment-info">
                    <h3>No Payment Information Available</h3>
                    <p>Payment information will appear here when therapy is completed.</p>
                </div>
            </div>
        );
    }

    const { totalSessions, totalAmount, therapyStatus, paymentRequired } = paymentInfo;

    return (
        <div className="payment-info-container">
            <div className="payment-header">
                <h2>💰 Payment Information</h2>
                <div className={`status-badge ${therapyStatus?.toLowerCase()}`}>
                    {therapyStatus || 'ACTIVE'}
                </div>
            </div>

            <div className="payment-details">
                <div className="detail-row">
                    <span className="label">Total Sessions:</span>
                    <span className="value">{totalSessions} sessions</span>
                </div>
                
                <div className="detail-row">
                    <span className="label">Rate per Session:</span>
                    <span className="value">$50.00</span>
                </div>
                
                <div className="detail-row total">
                    <span className="label">Total Amount:</span>
                    <span className="value">${totalAmount?.toFixed(2) || '0.00'}</span>
                </div>
            </div>

            {paymentRequired && (
                <div className="payment-alert">
                    <div className="alert-icon">⚠️</div>
                    <div className="alert-content">
                        <h3>Payment Required</h3>
                        <p>Your therapy has been completed. Please contact your therapist to arrange payment for your sessions.</p>
                        <div className="payment-summary">
                            <strong>Total Sessions:</strong> {totalSessions}<br/>
                            <strong>Total Amount:</strong> ${totalAmount?.toFixed(2) || '0.00'}
                        </div>
                    </div>
                </div>
            )}

            {!paymentRequired && (
                <div className="active-therapy">
                    <div className="active-icon">✅</div>
                    <div className="active-content">
                        <h3>Therapy is Active</h3>
                        <p>Your therapy sessions are ongoing. Payment information will be available when therapy is completed.</p>
                    </div>
                </div>
            )}

            <div className="payment-notes">
                <h4>Payment Notes:</h4>
                <ul>
                    <li>Rate: $50 per therapy session</li>
                    <li>Payment is due upon completion of therapy</li>
                    <li>Contact your therapist for payment arrangements</li>
                    <li>All sessions are tracked automatically</li>
                </ul>
            </div>
        </div>
    );
};

export default PaymentInfo; 