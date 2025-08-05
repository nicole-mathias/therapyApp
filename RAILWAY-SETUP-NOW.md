# 🚀 **Railway Setup - Do This Now**

## **Your Railway deployment is failing because it can't connect to PostgreSQL. Here's how to fix it:**

### **Step 1: Go to Railway Dashboard**
1. Visit [Railway.app](https://railway.app)
2. Open your project

### **Step 2: Add PostgreSQL Database**
1. Click **"New"** in your project
2. Select **"Database"**
3. Choose **"PostgreSQL"**
4. Wait for it to be created (takes 1-2 minutes)

### **Step 3: Get Database Connection String**
1. Click on your new PostgreSQL database
2. Go to **"Connect"** tab
3. Copy the **"Postgres Connection URL"**
   - It looks like: `postgresql://postgres:password@host:port/database`

### **Step 4: Set Environment Variables**
1. In your Railway project, go to **"Variables"** tab
2. Add these variables:

```
DATABASE_URL=postgresql://postgres:password@host:port/database
HUGGINGFACE_API_TOKEN=hf_your_token_here
ALLOWED_ORIGINS=https://zen-therapy.vercel.app
```

**Replace the DATABASE_URL with the actual connection string from Step 3**

### **Step 5: Wait for Redeploy**
- Railway will automatically redeploy
- Check the logs for: `✅ Configured for PostgreSQL`
- Should see successful startup

## **What I Fixed in the Code:**
- ✅ **Removed problematic DatabaseConfig** (was causing DataSource errors)
- ✅ **Added robust PostgreSQL detection** (automatic driver selection)
- ✅ **Better error handling** (clear logging)
- ✅ **Simplified configuration** (no more bean conflicts)

## **Expected Success:**
```
✅ Configured for PostgreSQL
Hibernate: HHH000412: Hibernate ORM core version 6.6.11.Final
Hibernate: HHH000204: Processing PersistenceUnitInfo [name: default]
Tomcat started on port 8080
```

## **If Still Having Issues:**
1. Check Railway logs for exact error
2. Make sure `DATABASE_URL` starts with `postgresql://`
3. Verify PostgreSQL database is running
4. Try manual redeploy

**The code has been pushed to GitHub, so Railway should automatically pick up the changes!** 