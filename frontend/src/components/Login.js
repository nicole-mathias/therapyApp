import React, { useState } from 'react';
import axios from '../utils/axiosConfig';
import './Login.css';

const Login = ({ onLogin }) => {
    const [isLogin, setIsLogin] = useState(true);
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        email: '',
        firstName: '',
        lastName: '',
        role: 'CLIENT',
        clientId: null
    });
    const [clients, setClients] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const fetchClients = async () => {
        try {
            const response = await axios.get('/therapy/clients');
            setClients(response.data);
        } catch (error) {
            console.error('Error fetching clients:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');

        try {
            const endpoint = isLogin ? '/auth/login' : '/auth/register';
            const response = await axios.post(endpoint, formData);

            if (response.data.success) {
                onLogin(response.data);
            } else {
                setError(response.data.message);
            }
        } catch (error) {
            setError('An error occurred. Please try again.');
            console.error('Auth error:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleRegisterClick = () => {
        setIsLogin(false);
        fetchClients();
    };

    const handleLoginClick = () => {
        setIsLogin(true);
        setError('');
    };

    return (
        <div className="login-container">
            <div className="login-card">
                <div className="login-header">
                    <h1>🧠 TherapyAI</h1>
                    <p>Welcome to your therapy platform</p>
                </div>

                <div className="login-tabs">
                    <button 
                        className={`tab ${isLogin ? 'active' : ''}`}
                        onClick={handleLoginClick}
                    >
                        Login
                    </button>
                    <button 
                        className={`tab ${!isLogin ? 'active' : ''}`}
                        onClick={handleRegisterClick}
                    >
                        Register
                    </button>
                </div>

                <form onSubmit={handleSubmit} className="login-form">
                    <div className="form-group">
                        <label>Username:</label>
                        <input
                            type="text"
                            name="username"
                            value={formData.username}
                            onChange={handleInputChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Password:</label>
                        <input
                            type="password"
                            name="password"
                            value={formData.password}
                            onChange={handleInputChange}
                            required
                        />
                    </div>

                    {!isLogin && (
                        <>
                            <div className="form-group">
                                <label>Email:</label>
                                <input
                                    type="email"
                                    name="email"
                                    value={formData.email}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>

                            <div className="form-row">
                                <div className="form-group">
                                    <label>First Name:</label>
                                    <input
                                        type="text"
                                        name="firstName"
                                        value={formData.firstName}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </div>

                                <div className="form-group">
                                    <label>Last Name:</label>
                                    <input
                                        type="text"
                                        name="lastName"
                                        value={formData.lastName}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </div>
                            </div>

                            <div className="form-group">
                                <label>Role:</label>
                                <select
                                    name="role"
                                    value={formData.role}
                                    onChange={handleInputChange}
                                    required
                                >
                                    <option value="CLIENT">Client</option>
                                    <option value="THERAPIST">Therapist</option>
                                </select>
                            </div>

                            {formData.role === 'CLIENT' && (
                                <div className="form-group">
                                    <label>Link to Existing Client (Optional):</label>
                                    <select
                                        name="clientId"
                                        value={formData.clientId || ''}
                                        onChange={handleInputChange}
                                    >
                                        <option value="">Select a client...</option>
                                        {clients.map(client => (
                                            <option key={client.id} value={client.id}>
                                                {client.name} (ID: {client.id})
                                            </option>
                                        ))}
                                    </select>
                                </div>
                            )}
                        </>
                    )}

                    {error && (
                        <div className="error-message">
                            {error}
                        </div>
                    )}

                    <button 
                        type="submit" 
                        className="submit-btn"
                        disabled={loading}
                    >
                        {loading ? 'Loading...' : (isLogin ? 'Login' : 'Register')}
                    </button>
                </form>

                <div className="login-footer">
                    <p>
                        {isLogin ? "Don't have an account? " : "Already have an account? "}
                        <button 
                            className="link-btn"
                            onClick={isLogin ? handleRegisterClick : handleLoginClick}
                        >
                            {isLogin ? 'Register' : 'Login'}
                        </button>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default Login; 