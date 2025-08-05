# 🚨 **EMERGENCY FIX - Railway Backend Deployment**

## **❌ Current Problem:**
Railway is trying to use MySQL driver with PostgreSQL URL, causing deployment failure.

## **✅ IMMEDIATE SOLUTION:**

### **Step 1: Go to Railway Dashboard**
1. Open [Railway Dashboard](https://railway.app/dashboard)
2. Click on your project
3. Go to **"Variables"** tab

### **Step 2: Set These Environment Variables EXACTLY:**

```
DATABASE_URL=postgresql://postgres:password@host:port/database
DB_DRIVER=org.postgresql.Driver
HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
HUGGINGFACE_API_TOKEN=hf_your_token_here
ALLOWED_ORIGINS=https://zen-therapy-frontend.vercel.app
```

**⚠️ CRITICAL:** Replace `DATABASE_URL` with your actual PostgreSQL connection string from Railway.

### **Step 3: Get Your PostgreSQL Connection String**
1. In Railway, click your **PostgreSQL database**
2. Go to **"Connect"** tab
3. Copy the **"Postgres Connection URL"**
4. Paste it as the `DATABASE_URL` value

### **Step 4: Wait for Redeployment**
- Railway will automatically redeploy
- Check logs for success message
- Should see: `✅ Configured for PostgreSQL`

## **🔧 Alternative Quick Fix:**

If the above doesn't work, try this simplified configuration:

### **Set Only These Variables:**
```
DATABASE_URL=your_postgresql_connection_string
DB_DRIVER=org.postgresql.Driver
HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
```

## **📋 Step-by-Step Instructions:**

### **1. Get PostgreSQL Connection String:**
```
1. Railway Dashboard → Your Project → PostgreSQL Database
2. Click "Connect" tab
3. Copy "Postgres Connection URL"
4. It looks like: postgresql://postgres:password@host:port/database
```

### **2. Set Environment Variables:**
```
Variable Name: DATABASE_URL
Value: [paste your PostgreSQL connection string]

Variable Name: DB_DRIVER
Value: org.postgresql.Driver

Variable Name: HIBERNATE_DIALECT
Value: org.hibernate.dialect.PostgreSQLDialect

Variable Name: HUGGINGFACE_API_TOKEN
Value: hf_your_token_here

Variable Name: ALLOWED_ORIGINS
Value: https://zen-therapy-frontend.vercel.app
```

### **3. Deploy:**
- Click **"Deploy"** or wait for auto-redeploy
- Check logs for success

## **🎯 Expected Success Logs:**
```
✅ Configured for PostgreSQL
Hibernate: HHH000412: Hibernate ORM core version 6.6.11.Final
Tomcat started on port 8080
```

## **🚨 If Still Failing:**

### **Option 1: Use Railway CLI**
```bash
# Install Railway CLI
npm install -g @railway/cli

# Login to Railway
railway login

# Link to your project
railway link

# Set variables
railway variables set DATABASE_URL="your_postgresql_url"
railway variables set DB_DRIVER="org.postgresql.Driver"
railway variables set HIBERNATE_DIALECT="org.hibernate.dialect.PostgreSQLDialect"

# Deploy
railway up
```

### **Option 2: Manual Database Setup**
1. Create a new PostgreSQL database in Railway
2. Get the connection string
3. Set the environment variables
4. Redeploy

## **📞 Need Help?**
If this doesn't work, please share:
1. Your Railway project URL
2. The exact error message
3. Screenshot of your environment variables

**This should fix your Railway deployment! 🚀** 