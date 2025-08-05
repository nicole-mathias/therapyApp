# 🧘 Zen Therapy

A full-stack therapy application with AI-powered chat, crisis detection, and professional client management.

## ✨ Features

### 🤖 **AI-Powered Therapy Chat**
- Real-time conversation with AI therapist
- Context-aware responses using Hugging Face models
- Crisis detection and emergency alerts
- Sentiment analysis and mood tracking

### 👥 **Client Management**
- Therapist dashboard for client management
- Self-registration for clients
- Session tracking and payment management
- Emergency contact management

### 📊 **Analytics & Reporting**
- Daily mood logging
- AI-powered summaries and insights
- Keyword extraction and sentiment analysis
- Crisis detection and alerts

### 🔒 **Security & Safety**
- User authentication and role-based access
- Crisis detection with emergency notifications
- HIPAA-compliant data handling
- Secure API communication

## 🛠️ Tech Stack

### **Backend**
- **Spring Boot 3.4.4** - Java framework
- **Spring Data JPA** - Database ORM
- **MySQL/PostgreSQL** - Database
- **Hugging Face API** - AI integration
- **Maven** - Build tool

### **Frontend**
- **React 19** - UI framework
- **Axios** - HTTP client
- **CSS3** - Styling
- **Vercel** - Deployment

### **Infrastructure**
- **Railway** - Backend hosting
- **Vercel** - Frontend hosting
- **Hugging Face** - AI API
- **GitHub** - Version control

## 🚀 Quick Start

### **Local Development**

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/zen-therapy.git
cd zen-therapy
```

2. **Set up MySQL Database**
```bash
# Create database
mysql -u root -p
CREATE DATABASE therapy;
```

3. **Configure Environment**
```bash
# Backend
cd backend
# Update application.properties with your database credentials
```

4. **Start Backend**
```bash
cd backend
mvn spring-boot:run
```

5. **Start Frontend**
```bash
cd frontend
npm install
npm start
```

6. **Access the Application**
- Frontend: http://localhost:3000
- Backend: http://localhost:8080

### **Production Deployment**

1. **Run the setup script**
```bash
./setup-production.sh
```

2. **Follow the deployment guide**
- See `DEPLOYMENT.md` for detailed instructions

## 📋 Prerequisites

- **Java 17+**
- **Node.js 18+**
- **MySQL 8.0+**
- **Git**

## 🔧 Configuration

### **Environment Variables**

#### **Backend (Railway)**
```env
DATABASE_URL=your_database_url
HUGGINGFACE_API_TOKEN=your_hf_token
ALLOWED_ORIGINS=https://zen-therapy.vercel.app
PORT=8080
```

#### **Frontend (Vercel)**
```env
REACT_APP_API_URL=https://zen-therapy-backend.railway.app
REACT_APP_NAME=Zen Therapy
```

## 🗄️ Database Schema

### **Users Table**
- User authentication and roles
- Client-therapist relationships

### **Clients Table**
- Client information and medical history
- Session tracking and payment status

### **Chat Messages Table**
- Therapy conversation history
- Crisis detection results

### **Daily Logs Table**
- Client mood and activity tracking
- AI-generated summaries

## 🔐 Security Features

- **JWT Authentication**
- **Role-based Access Control**
- **CORS Configuration**
- **Input Validation**
- **SQL Injection Prevention**

## 🚨 Crisis Detection

The application includes advanced crisis detection:

- **Keyword Analysis** - Detects crisis-related terms
- **Sentiment Analysis** - Monitors emotional state
- **Emergency Alerts** - Notifies therapists and contacts
- **Crisis Dashboard** - Real-time monitoring

## 📊 API Endpoints

### **Authentication**
- `POST /auth/login` - User login
- `POST /auth/register` - User registration

### **Client Management**
- `GET /therapy/clients` - List all clients
- `POST /therapy/clients` - Create new client
- `PUT /therapy/clients/{id}` - Update client

### **Therapy Chat**
- `POST /therapy/chat/send` - Send message
- `GET /therapy/chat/{clientId}` - Get chat history

### **Analytics**
- `POST /therapy/logs` - Save daily log
- `GET /therapy/analytics/{clientId}` - Get insights

## 🧪 Testing

### **Backend Tests**
```bash
cd backend
mvn test
```

### **Frontend Tests**
```bash
cd frontend
npm test
```

## 📈 Monitoring

- **Railway Dashboard** - Backend performance
- **Vercel Analytics** - Frontend metrics
- **Application Logs** - Error tracking

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

- **Documentation**: See `DEPLOYMENT.md`
- **Issues**: GitHub Issues
- **Email**: support@zentherapy.com

## 🎯 Roadmap

- [ ] Mobile app (React Native)
- [ ] Video therapy sessions
- [ ] Advanced AI models
- [ ] Payment integration
- [ ] Multi-language support

---

**Built with ❤️ for mental health support**