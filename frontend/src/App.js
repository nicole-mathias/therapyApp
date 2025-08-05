import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Client from './Client';
import TherapyChat from './components/TherapyChat';
import CrisisDashboard from './components/CrisisDashboard';
import ClientDashboard from './components/ClientDashboard';
import './App.css';

function App() {
  const [user, setUser] = useState(null);
  const [selectedClient, setSelectedClient] = useState(null);

  const handleLogin = (loginData) => {
    setUser(loginData.user);
    if (loginData.client) {
      setSelectedClient(loginData.client);
    }
  };

  const handleLogout = () => {
    setUser(null);
    setSelectedClient(null);
  };

  const handleClientSelect = (client) => {
    setSelectedClient(client);
  };

  // If not logged in, show login page
  if (!user) {
    return <Login onLogin={handleLogin} />;
  }

  return (
    <Router>
      <div className="App">
        <nav className="navbar">
          <div className="nav-brand">
            <h1>🧘 Zen Therapy</h1>
          </div>
          <div className="nav-user">
            <span>Welcome, {user.firstName} {user.lastName}</span>
            <button onClick={handleLogout} className="logout-btn">
              Logout
            </button>
          </div>
        </nav>

        <div className="main-content">
          <Routes>
            {user.role === 'THERAPIST' ? (
              // Therapist Routes
              <>
                <Route 
                  path="/" 
                  element={
                    <Client 
                      onClientSelect={handleClientSelect} 
                    />
                  } 
                />
                <Route 
                  path="/chat/:clientId" 
                  element={
                    selectedClient ? (
                      <TherapyChat client={selectedClient} />
                    ) : (
                      <Navigate to="/" replace />
                    )
                  } 
                />
                <Route 
                  path="/crisis" 
                  element={<CrisisDashboard />} 
                />
              </>
            ) : (
              // Client Routes
              <>
                <Route 
                  path="/" 
                  element={
                    <ClientDashboard 
                      user={user} 
                      client={selectedClient} 
                    />
                  } 
                />
                <Route 
                  path="/chat" 
                  element={
                    selectedClient ? (
                      <TherapyChat client={selectedClient} />
                    ) : (
                      <Navigate to="/" replace />
                    )
                  } 
                />
              </>
            )}
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
