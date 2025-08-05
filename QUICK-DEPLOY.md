# 🚀 Quick Deployment Guide

## ✅ **Code is Ready!**

Your code has been pushed to GitHub: `https://github.com/nicole-mathias/therapyApp`

## 🚂 **Deploy Backend (Railway)**

1. **Go to [Railway.app](https://railway.app)**
2. **Sign up/Login** (free)
3. **Click "New Project"**
4. **Select "Deploy from GitHub repo"**
5. **Choose**: `nicole-mathias/therapyApp`
6. **Wait for deployment** (should work now with fixed config)

## 🗄️ **Add Database**

1. **In Railway project, click "New"**
2. **Select "Database" → "PostgreSQL"**
3. **Railway will auto-connect it to your app**

## ⚙️ **Set Environment Variables**

In Railway project settings, add:

```
DATABASE_URL=postgresql://username:password@host:port/database
HUGGINGFACE_API_TOKEN=hf_your_token_here
ALLOWED_ORIGINS=https://zen-therapy.vercel.app
```

**Note**: Replace the `DATABASE_URL` with the actual connection string from your PostgreSQL database in Railway.

## 🎨 **Deploy Frontend (Vercel)**

1. **Go to [Vercel.com](https://vercel.com)**
2. **Sign up/Login** (free)
3. **Click "New Project"**
4. **Import GitHub repo**: `nicole-mathias/therapyApp`
5. **Set root directory**: `frontend`
6. **Add environment variable**:
   - **Name**: `REACT_APP_API_URL`
   - **Value**: Your Railway backend URL (e.g., `https://your-app.railway.app`)

## 🎉 **Done!**

Your app will be live at:
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

## 🐛 **If Railway Still Fails**

Try the Dockerfile approach:
1. In Railway project settings
2. Go to "Settings" → "Deploy"
3. Set "Builder" to "Dockerfile"
4. Redeploy 