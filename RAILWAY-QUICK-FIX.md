# 🚨 Railway Database Connection Fix

## **The Problem**
Railway is failing to connect to PostgreSQL because the `DATABASE_URL` environment variable isn't set correctly.

## **Quick Fix Steps**

### **Step 1: Add PostgreSQL Database in Railway**
1. Go to your Railway project
2. Click **"New"** → **"Database"** → **"PostgreSQL"**
3. Wait for it to be created

### **Step 2: Get the Connection String**
1. Click on your PostgreSQL database
2. Go to **"Connect"** tab
3. Copy the **"Postgres Connection URL"**

### **Step 3: Set Environment Variables**
In Railway project settings, add these environment variables:

```
DATABASE_URL=postgresql://username:password@host:port/database
HUGGINGFACE_API_TOKEN=hf_your_token_here
ALLOWED_ORIGINS=https://zen-therapy.vercel.app
```

**Replace the DATABASE_URL with the actual connection string from Step 2**

### **Step 4: Redeploy**
1. Railway will automatically redeploy
2. Check the logs for: `✅ Configured for PostgreSQL`

## **Expected Logs**
You should see:
```
✅ Configured for PostgreSQL
Hibernate: HHH000412: Hibernate ORM core version 6.6.11.Final
Hibernate: HHH000204: Processing PersistenceUnitInfo [name: default]
```

## **If Still Failing**
1. Check Railway logs for the exact error
2. Make sure `DATABASE_URL` starts with `postgresql://`
3. Verify the PostgreSQL database is running
4. Try redeploying manually

## **Common Issues**
- ❌ `DATABASE_URL` not set
- ❌ PostgreSQL database not created
- ❌ Wrong connection string format
- ❌ Database not accessible

## **Success Indicators**
- ✅ `✅ Configured for PostgreSQL` in logs
- ✅ Application starts without database errors
- ✅ Hibernate connects successfully 