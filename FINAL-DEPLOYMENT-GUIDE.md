# 🚀 Final Deployment Guide - Zen Therapy

## ✅ **Latest Fixes Applied**

1. **Removed problematic DatabaseConfig** - No more DataSource bean conflicts
2. **Added Railway-specific configuration** - Automatic PostgreSQL detection
3. **Simplified database setup** - Let Spring Boot handle everything
4. **Updated application properties** - Environment variable support

## 🚂 **Deploy Backend (Railway)**

### **Step 1: Go to Railway**
1. Visit [Railway.app](https://railway.app)
2. Sign up/Login (free)
3. Click **"New Project"**

### **Step 2: Deploy from GitHub**
1. Select **"Deploy from GitHub repo"**
2. Choose: `nicole-mathias/therapyApp`
3. Wait for deployment (should work now!)

### **Step 3: Add PostgreSQL Database**
1. In Railway project, click **"New"**
2. Select **"Database"** → **"PostgreSQL"**
3. Railway will auto-connect it to your app

### **Step 4: Set Environment Variables**
In Railway project settings, add:

```
DATABASE_URL=postgresql://username:password@host:port/database
HUGGINGFACE_API_TOKEN=hf_your_token_here
ALLOWED_ORIGINS=https://zen-therapy.vercel.app
```

**Note**: Replace `DATABASE_URL` with the actual connection string from your PostgreSQL database in Railway.

## 🎨 **Deploy Frontend (Vercel)**

### **Step 1: Go to Vercel**
1. Visit [Vercel.com](https://vercel.com)
2. Sign up/Login (free)
3. Click **"New Project"**

### **Step 2: Import Repository**
1. Import GitHub repo: `nicole-mathias/therapyApp`
2. Set **Root Directory**: `frontend`
3. Click **"Deploy"**

### **Step 3: Set Environment Variable**
Add this environment variable in Vercel:
- **Name**: `REACT_APP_API_URL`
- **Value**: Your Railway backend URL (e.g., `https://your-app.railway.app`)

## 🎉 **Your App Will Be Live At**

- **Frontend**: `https://zen-therapy.vercel.app`
- **Backend**: `https://your-app.railway.app`

## 🔧 **Database Connection String**

The `DATABASE_URL` will look like:
```
postgresql://postgres:password@host:port/database
```

**Where to find it:**
1. In Railway, click your PostgreSQL database
2. Go to "Connect" tab
3. Copy the "Postgres Connection URL"

## ✅ **What's Fixed**

- ✅ **No more DataSource bean errors**
- ✅ **Automatic PostgreSQL detection**
- ✅ **Railway-specific configuration**
- ✅ **Environment variable support**
- ✅ **Simplified deployment process**

## 🐛 **If Still Having Issues**

### **Check Railway Logs**
1. Go to Railway dashboard
2. Click on your service
3. Check "Logs" tab for errors

### **Manual Redeploy**
1. In Railway project settings
2. Go to "Settings" → "Deploy"
3. Click "Redeploy"

### **Verify Environment Variables**
Make sure these are set in Railway:
- `DATABASE_URL` (from PostgreSQL)
- `HUGGINGFACE_API_TOKEN`
- `ALLOWED_ORIGINS`

## 📞 **Next Steps**

1. **Wait for Railway to redeploy** (automatic)
2. **Check the logs** for successful startup
3. **Test the API endpoints**
4. **Deploy frontend to Vercel**
5. **Connect frontend to backend**

## 🎯 **Expected Result**

Your Zen Therapy app should now deploy successfully on Railway with:
- ✅ Automatic PostgreSQL connection
- ✅ No DataSource conflicts
- ✅ Proper environment configuration
- ✅ Working API endpoints 