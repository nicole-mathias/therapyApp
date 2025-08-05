import React, {useState,useEffect} from "react";
import axios from "./utils/axiosConfig";
import './Client.css';

function Client({ onClientSelect }){

    const [client, setClient] = useState([]);
    const [newClient, setNewClient] = useState({
        name: '',
        age: '',
        diagnosis: '',
        emergencyContactName: '',
        emergencyContactPhone: '',
        emergencyContactRelationship: ''
    });
    const [showAddForm, setShowAddForm] = useState(false);

    useEffect(() => {
        fetchClients();
    }, [])


    const handleEdit = (id) => {
        console.log(id);
    }

    const handleLinkClient = async (userClient) => {
        try {
            // Create a client record for the self-registered user
            const clientData = {
                name: userClient.name,
                age: null,
                diagnosis: 'Self-registered client',
                emergencyContactName: null,
                emergencyContactPhone: null,
                emergencyContactRelationship: null
            };
            
            const response = await axios.post('/therapy/clients', clientData);
            const newClientRecord = response.data;
            
            // Link the user to the new client record
            await axios.post(`/auth/users/${userClient.userId}/link-client`, {
                clientId: newClientRecord.id
            });
            
            // Refresh the client list
            const [clientsRes, usersRes] = await Promise.all([
                axios.get('/therapy/clients'),
                axios.get('/auth/users')
            ]);
            
            const clientRecords = clientsRes.data || [];
            const allUsers = usersRes.data?.users || [];
            const clientUsers = allUsers.filter(user => user.role === 'CLIENT');
            
            const allClients = [...clientRecords];
            
            clientUsers.forEach(user => {
                if (!user.client) {
                    allClients.push({
                        id: `user-${user.id}`,
                        name: `${user.firstName} ${user.lastName}`,
                        age: null,
                        diagnosis: 'Self-registered client',
                        emergencyContactName: null,
                        emergencyContactPhone: null,
                        emergencyContactRelationship: null,
                        riskLevel: 0,
                        therapistNotes: null,
                        isUserClient: true,
                        userId: user.id,
                        username: user.username,
                        email: user.email
                    });
                }
            });
            
            setClient(allClients);
            alert(`Successfully linked ${userClient.name} to a client record!`);
        } catch (error) {
            console.error('Error linking client:', error);
            alert('Failed to link client. Please try again.');
        }
    }

    const handleEndTherapy = async (client) => {
        try {
            const reason = prompt('Reason for ending therapy:');
            if (!reason) return;
            
            const response = await axios.post(`/therapy/management/client/${client.id}/end-therapy`, {
                reason: reason,
                notes: `Therapy ended by therapist on ${new Date().toLocaleDateString()}`
            });
            
            if (response.data.success) {
                const totalSessions = response.data.totalSessions;
                const totalAmount = response.data.totalAmount;
                
                alert(`Therapy ended successfully!\n\nTotal Sessions: ${totalSessions}\nTotal Amount: $${totalAmount.toFixed(2)}\n\nPayment notification has been sent to the client.`);
                
                // Refresh client list
                fetchClients();
            } else {
                alert('Failed to end therapy: ' + response.data.message);
            }
        } catch (error) {
            console.error('Error ending therapy:', error);
            alert('Failed to end therapy. Please try again.');
        }
    }

    const handleViewPayment = async (client) => {
        try {
            const response = await axios.get(`/therapy/management/client/${client.id}/payment`);
            
            if (response.data.success) {
                const { totalSessions, totalAmount, therapyStatus, paymentRequired } = response.data;
                
                let message = `Payment Information for ${client.name}:\n\n`;
                message += `Total Sessions: ${totalSessions}\n`;
                message += `Total Amount: $${totalAmount.toFixed(2)}\n`;
                message += `Status: ${therapyStatus}\n\n`;
                
                if (paymentRequired) {
                    message += '⚠️ PAYMENT REQUIRED\n';
                    message += 'The client has been notified about the payment.\n';
                    message += 'Rate: $50 per session';
                } else {
                    message += '✅ Therapy is still active';
                }
                
                alert(message);
            } else {
                alert('Failed to get payment info: ' + response.data.message);
            }
        } catch (error) {
            console.error('Error getting payment info:', error);
            alert('Failed to get payment information. Please try again.');
        }
    }

    const fetchClients = async () => {
        try {
            // Fetch both client records and client users
            const [clientsRes, usersRes] = await Promise.all([
                axios.get('/therapy/clients'),
                axios.get('/auth/users')
            ]);
            
            const clientRecords = clientsRes.data || [];
            const allUsers = usersRes.data?.users || [];
            const clientUsers = allUsers.filter(user => user.role === 'CLIENT');
            
            // Combine client records with client users who don't have client records
            const allClients = [...clientRecords];
            
            clientUsers.forEach(user => {
                // If user doesn't have a client record, create a display entry
                if (!user.client) {
                    allClients.push({
                        id: `user-${user.id}`,
                        name: `${user.firstName} ${user.lastName}`,
                        age: null,
                        diagnosis: 'Self-registered client',
                        emergencyContactName: null,
                        emergencyContactPhone: null,
                        emergencyContactRelationship: null,
                        riskLevel: 0,
                        therapistNotes: null,
                        isUserClient: true,
                        userId: user.id,
                        username: user.username,
                        email: user.email
                    });
                }
            });
            
            setClient(allClients);
        } catch (error) {
            console.error('Error fetching clients:', error);
        }
    }

    const handleClientSelect = (client) => {
        onClientSelect(client);
    }

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewClient(prev => ({
            ...prev,
            [name]: value
        }));
    }

    const addClient = async () => {
        try {
            const response = await axios.post('/therapy/clients', newClient);
            setClient(prev => [...prev, response.data]);
            setNewClient({
                name: '',
                age: '',
                diagnosis: '',
                emergencyContactName: '',
                emergencyContactPhone: '',
                emergencyContactRelationship: ''
            });
            setShowAddForm(false);
        } catch (error) {
            console.error('Error adding client:', error);
        }
    }


    // function clients(){
    //     axios.get('/therapy/clients')
    //     .then(res => {
    //         console.log(res)
    //     }
        
    // }

    return(
        <div className="client-container">
            <div className="client-header">
                <h1>👥 Client Management</h1>
                <button 
                    onClick={() => setShowAddForm(!showAddForm)}
                    className="add-client-btn"
                >
                    {showAddForm ? 'Cancel' : '+ Add Client'}
                </button>
            </div>

            {showAddForm && (
                <div className="add-client-form">
                    <h3>Add New Client</h3>
                    <div className="form-grid">
                        <div className="form-group">
                            <label>Name:</label>
                            <input
                                type="text"
                                name="name"
                                value={newClient.name}
                                onChange={handleInputChange}
                                placeholder="Client name"
                            />
                        </div>
                        <div className="form-group">
                            <label>Age:</label>
                            <input
                                type="number"
                                name="age"
                                value={newClient.age}
                                onChange={handleInputChange}
                                placeholder="Age"
                            />
                        </div>
                        <div className="form-group">
                            <label>Diagnosis:</label>
                            <input
                                type="text"
                                name="diagnosis"
                                value={newClient.diagnosis}
                                onChange={handleInputChange}
                                placeholder="Diagnosis (optional)"
                            />
                        </div>
                        <div className="form-group">
                            <label>Emergency Contact Name:</label>
                            <input
                                type="text"
                                name="emergencyContactName"
                                value={newClient.emergencyContactName}
                                onChange={handleInputChange}
                                placeholder="Emergency contact name"
                            />
                        </div>
                        <div className="form-group">
                            <label>Emergency Contact Phone:</label>
                            <input
                                type="tel"
                                name="emergencyContactPhone"
                                value={newClient.emergencyContactPhone}
                                onChange={handleInputChange}
                                placeholder="Emergency contact phone"
                            />
                        </div>
                        <div className="form-group">
                            <label>Relationship:</label>
                            <input
                                type="text"
                                name="emergencyContactRelationship"
                                value={newClient.emergencyContactRelationship}
                                onChange={handleInputChange}
                                placeholder="Relationship to client"
                            />
                        </div>
                    </div>
                    <button onClick={addClient} className="submit-btn">
                        Add Client
                    </button>
                </div>
            )}

            <div className="clients-table">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Age</th>
                            <th>Diagnosis</th>
                            <th>Sessions</th>
                            <th>Status</th>
                            <th>Risk Level</th>
                            <th>Emergency Contact</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {client.map(c => (
                            <tr key={c.id} className={`client-row ${c.isUserClient ? 'user-client' : 'regular-client'}`}>
                                <td>{c.id}</td>
                                <td>
                                    <div className="client-name">
                                        {c.name}
                                        {c.isUserClient && (
                                            <div className="client-details">
                                                <small>Username: {c.username}</small>
                                                <small>Email: {c.email}</small>
                                            </div>
                                        )}
                                    </div>
                                </td>
                                <td>
                                    <span className={`client-type ${c.isUserClient ? 'self-registered' : 'therapist-added'}`}>
                                        {c.isUserClient ? 'Self-Registered' : 'Therapist Added'}
                                    </span>
                                </td>
                                <td>{c.age || 'N/A'}</td>
                                <td>{c.diagnosis || 'N/A'}</td>
                                <td>
                                    <span className="session-count">
                                        {c.sessionCount || 0} sessions
                                    </span>
                                </td>
                                <td>
                                    <span className={`therapy-status ${c.therapyStatus?.toLowerCase() || 'active'}`}>
                                        {c.therapyStatus || 'ACTIVE'}
                                    </span>
                                </td>
                                <td>
                                    <span className={`risk-level risk-${c.riskLevel || 0}`}>
                                        {c.riskLevel || 0}/5
                                    </span>
                                </td>
                                <td>
                                    {c.emergencyContactName ? (
                                        <div className="emergency-info">
                                            <div>{c.emergencyContactName}</div>
                                            <div className="phone">{c.emergencyContactPhone}</div>
                                        </div>
                                    ) : 'Not set'}
                                </td>
                                <td>
                                    <div className="action-buttons">
                                        <button 
                                            onClick={() => handleClientSelect(c)}
                                            className="chat-btn"
                                            disabled={c.isUserClient && !c.client || c.therapyStatus === 'COMPLETED'}
                                            title={c.isUserClient && !c.client ? 'Client needs to be linked to a client record first' : 
                                                   c.therapyStatus === 'COMPLETED' ? 'Therapy has been completed' : ''}
                                        >
                                            💬 Start Chat
                                        </button>
                                        <button 
                                            onClick={() => handleEdit(c.id)}
                                            className="edit-btn"
                                        >
                                            ✏️ Edit
                                        </button>
                                        {c.isUserClient && !c.client && (
                                            <button 
                                                onClick={() => handleLinkClient(c)}
                                                className="link-btn"
                                                title="Link user to client record"
                                            >
                                                🔗 Link
                                            </button>
                                        )}
                                        {!c.isUserClient && c.therapyStatus === 'ACTIVE' && (
                                            <button 
                                                onClick={() => handleEndTherapy(c)}
                                                className="end-therapy-btn"
                                                title="End therapy and calculate payment"
                                            >
                                                🏁 End Therapy
                                            </button>
                                        )}
                                        {c.therapyStatus === 'COMPLETED' && (
                                            <button 
                                                onClick={() => handleViewPayment(c)}
                                                className="payment-btn"
                                                title="View payment information"
                                            >
                                                💰 Payment
                                            </button>
                                        )}
                                    </div>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
export default Client;