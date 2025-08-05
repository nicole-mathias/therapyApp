# 🗄️ Database Setup for Railway Deployment

## 🚀 **Step-by-Step Database Setup**

### **Option A: Railway PostgreSQL (Recommended)**

1. **Deploy Backend to Railway:**
   - Go to [Railway.app](https://railway.app)
   - Create new project
   - Deploy from GitHub: `nicole-mathias/therapyApp`

2. **Add PostgreSQL Database:**
   - In your Railway project, click **"New"**
   - Select **"Database"** → **"PostgreSQL"**
   - Railway will automatically connect it to your app

3. **Get Connection String:**
   - Click on your PostgreSQL database
   - Go to **"Connect"** tab
   - Copy the **"Postgres Connection URL"**
   - Format: `postgresql://username:password@host:port/database`

4. **Set Environment Variables:**
   - In Railway project settings
   - Add environment variable:
     - **Name**: `DATABASE_URL`
     - **Value**: `postgresql://username:password@host:port/database` (from step 3)

### **Option B: Use Your MySQL (Alternative)**

If you want to keep using your MySQL database:

1. **Set Environment Variables in Railway:**
   ```
   DATABASE_URL=jdbc:mysql://your-mysql-host:3306/therapy
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   ```

2. **Make sure your MySQL is accessible from Railway**

## 🔧 **Environment Variables for Railway**

Add these to your Railway project settings:

```
DATABASE_URL=postgresql://username:password@host:port/database
HUGGINGFACE_API_TOKEN=hf_your_token_here
ALLOWED_ORIGINS=https://zen-therapy.vercel.app
```

## ✅ **What Happens Next**

1. **Railway will automatically:**
   - Detect PostgreSQL from `DATABASE_URL`
   - Use PostgreSQL dialect
   - Create tables automatically (`spring.jpa.hibernate.ddl-auto=update`)

2. **Your app will work with:**
   - PostgreSQL in production (Railway)
   - MySQL in development (local)

## 🐛 **Troubleshooting**

### **If Railway deployment fails:**
1. Check that `DATABASE_URL` is set correctly
2. Ensure PostgreSQL database is added to Railway project
3. Verify environment variables are set

### **If database connection fails:**
1. Check Railway logs for connection errors
2. Verify the connection string format
3. Ensure database is running

## 📋 **Quick Commands**

```bash
# Check if backend is running locally
curl http://localhost:8080/therapy/clients

# Check Railway deployment
# Go to Railway dashboard and check logs
``` 