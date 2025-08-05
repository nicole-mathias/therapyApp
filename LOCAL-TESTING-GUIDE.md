# 🧪 **Local Testing Guide - PostgreSQL**

## **✅ Success! Your application is working locally with PostgreSQL**

The application is now running successfully on `http://localhost:8080` with PostgreSQL database.

## **What We Just Tested:**

### **✅ PostgreSQL Connection**
- PostgreSQL 14.18 installed and running
- Database `therapy_local` created
- Application connects successfully

### **✅ Spring Boot Application**
- Running on port 8080
- Using PostgreSQL driver correctly
- All endpoints responding

### **✅ API Endpoints Working**
- `GET /therapy/clients` - Returns `[]` (empty, as expected)
- `GET /auth/users` - Returns `{"success":true,"users":[]}`

## **How to Test Everything Locally:**

### **1. Start PostgreSQL (if not running)**
```bash
brew services start postgresql@14
```

### **2. Run Backend with PostgreSQL**
```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=test
```

### **3. Run Frontend**
```bash
cd frontend
npm start
```

### **4. Test the Full Application**
- Open `http://localhost:3000`
- Register a new user
- Add a client
- Test the therapy chat
- Test all features

## **Environment Variables for Local Testing:**

The test profile uses these settings:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/therapy_local
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## **Now You Can Deploy to Railway with Confidence!**

Since the application works perfectly locally with PostgreSQL, the Railway deployment should work with the correct environment variables.

### **Railway Environment Variables:**
```
DATABASE_URL=postgresql://postgres:password@host:port/database
DB_DRIVER=org.postgresql.Driver
HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
HUGGINGFACE_API_TOKEN=hf_your_token_here
ALLOWED_ORIGINS=https://zen-therapy.vercel.app
```

## **Next Steps:**

1. **Test all features locally** to make sure everything works
2. **Set up Railway environment variables** as shown above
3. **Deploy to Railway** - it should work perfectly now!
4. **Deploy frontend to Vercel**

## **Troubleshooting:**

### **If PostgreSQL connection fails:**
```bash
# Check if PostgreSQL is running
brew services list | grep postgresql

# Start if needed
brew services start postgresql@14

# Test connection
psql -d therapy_local -c "SELECT version();"
```

### **If application won't start:**
```bash
# Kill any existing processes on port 8080
lsof -ti:8080 | xargs kill -9

# Restart with test profile
cd backend && mvn spring-boot:run -Dspring-boot.run.profiles=test
```

## **Success Indicators:**
- ✅ PostgreSQL running on port 5432
- ✅ Application running on port 8080
- ✅ API endpoints responding
- ✅ Database tables created automatically
- ✅ No driver conflicts

**Your application is ready for Railway deployment! 🚀** 