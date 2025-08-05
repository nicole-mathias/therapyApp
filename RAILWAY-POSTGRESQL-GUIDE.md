# 🗺️ **Railway PostgreSQL Setup Guide**

## **📍 Where to Find PostgreSQL on Railway:**

### **Method 1: Project Dashboard**
1. Go to [Railway Dashboard](https://railway.app/dashboard)
2. Click your project
3. Look for:
   - **"New"** button (usually top-right)
   - **"Add Service"** button
   - **"Deploy"** button

### **Method 2: Services Tab**
1. In your project, look for **"Services"** tab
2. Click **"Add Service"** or **"New Service"**
3. Select **"Database"** or **"PostgreSQL"**

### **Method 3: Sidebar Navigation**
1. Look for a **"+"** icon in the sidebar
2. Click it and select **"Database"**
3. Choose **"PostgreSQL"**

### **Method 4: If You See "Templates"**
1. Look for **"Templates"** or **"Quick Deploy"**
2. Search for **"PostgreSQL"**
3. Click **"Deploy"**

## **🔍 Common Locations:**

```
Railway Dashboard
├── Your Project
│   ├── "New" button (top-right)
│   ├── "Add Service" button
│   ├── "Services" tab
│   └── "+" icon (sidebar)
```

## **📋 What to Look For:**
- **"Database"** service
- **"PostgreSQL"** service  
- **"Add Service"** button
- **"New Service"** button
- **"Deploy"** button

## **🚨 If You Still Can't Find It:**

**Option A: Use Railway CLI**
```bash
# Install Railway CLI
npm install -g @railway/cli

# Login to Railway
railway login

# Link to your project
railway link

# Add PostgreSQL service
railway service add postgresql
```

**Option B: Check if PostgreSQL Already Exists**
- Look in your project for any existing database service
- It might be named differently or already added

**Option C: Contact Railway Support**
- Railway's interface changes frequently
- You might need to check their latest documentation

## **✅ Once You Find PostgreSQL:**
1. Click **"Deploy"** or **"Add"**
2. Wait for it to be created
3. Go to the PostgreSQL service
4. Click **"Connect"** tab
5. Copy the **"Postgres Connection URL"**

## **🔧 Set Environment Variables:**
Once you have the connection string, set these in Railway Variables:
```
DATABASE_URL=your_postgresql_connection_string
DB_DRIVER=org.postgresql.Driver
HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
``` 