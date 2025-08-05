# 🚂 Railway Deployment Guide

## ✅ **Fixed Issues**

The Railway deployment issues have been fixed with:
- ✅ Added `nixpacks.toml` for better build detection
- ✅ Updated `railway.json` with proper build commands
- ✅ Added `.railwayignore` to exclude unnecessary files
- ✅ Updated `Dockerfile` for Railway compatibility

## 🚀 **Deploy to Railway (Fixed)**

### **Step 1: Go to Railway**
1. Visit [Railway.app](https://railway.app)
2. Sign up/Login (free)
3. Click **"New Project"**

### **Step 2: Deploy from GitHub**
1. Select **"Deploy from GitHub repo"**
2. Choose your repository: `nicole-mathias/therapyApp`
3. Railway will now properly detect the Java project

### **Step 3: Add Database**
1. In your Railway project, click **"New"**
2. Select **"Database"** → **"PostgreSQL"**
3. Railway will automatically connect it to your app

### **Step 4: Set Environment Variables**
In Railway dashboard, go to your backend service and add:

```env
DATABASE_URL=postgresql://username:password@host:port/database
HUGGINGFACE_API_TOKEN=hf_your_token_here
ALLOWED_ORIGINS=https://zen-therapy.vercel.app
PORT=8080
```

### **Step 5: Get Your Backend URL**
- Railway will provide a URL like: `https://zen-therapy-backend.railway.app`

## 🔧 **Alternative: Use Docker**

If Nixpacks still fails, Railway can use the Dockerfile:

1. In Railway project settings
2. Go to **"Settings"** → **"Deploy"**
3. Change **"Builder"** to **"Dockerfile"**
4. Set **"Dockerfile Path"** to `backend/Dockerfile`

## 🧪 **Test Your Deployment**

Once deployed, test your backend:

```bash
curl https://your-railway-app.railway.app/therapy/clients
```

## 🎯 **Next: Deploy Frontend**

After Railway backend is working:

1. Go to [Vercel.com](https://vercel.com)
2. Import your GitHub repository
3. Set **Root Directory**: `frontend`
4. Add environment variables:
   ```env
   REACT_APP_API_URL=https://your-railway-app.railway.app
   REACT_APP_NAME=Zen Therapy
   ```

## 🚨 **Troubleshooting**

### **If Nixpacks still fails:**
1. Check Railway logs for specific errors
2. Try the Dockerfile approach
3. Ensure all files are committed to GitHub

### **If build succeeds but app doesn't start:**
1. Check environment variables
2. Verify database connection
3. Check Railway logs for startup errors

## 📞 **Support**

- **Railway Docs**: [docs.railway.app](https://docs.railway.app)
- **Railway Discord**: [discord.gg/railway](https://discord.gg/railway)
- **GitHub Issues**: Create an issue in your repository

---

**Your app should now deploy successfully on Railway! 🎉** 