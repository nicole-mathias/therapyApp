# 🚀 Zen Therapy - Deployment Guide

## 📋 **Prerequisites**

1. **GitHub Account** - For code repository
2. **Railway Account** - For backend & database (free tier)
3. **Vercel Account** - For frontend (free tier)
4. **Hugging Face Account** - For AI API (free tier)

## 🗄️ **Database Setup**

### **Option A: Railway PostgreSQL (Recommended)**
1. Go to [Railway.app](https://railway.app)
2. Create new project
3. Add PostgreSQL database
4. Copy the connection string

### **Option B: External MySQL**
1. Use your existing MySQL server
2. Update connection string in Railway environment variables

## 🔧 **Backend Deployment (Railway)**

### **Step 1: Prepare Repository**
```bash
# Ensure all code is committed to GitHub
git add .
git commit -m "Production ready"
git push origin main
```

### **Step 2: Deploy to Railway**
1. Go to [Railway.app](https://railway.app)
2. Click "New Project" → "Deploy from GitHub repo"
3. Select your repository
4. Set the following environment variables:

```env
# Database Configuration
DATABASE_URL=your_railway_postgresql_url
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password

# Hugging Face API
HUGGINGFACE_API_URL=https://api-inference.huggingface.co/models/gpt2
HUGGINGFACE_API_TOKEN=your_hf_token

# CORS Configuration
ALLOWED_ORIGINS=https://zen-therapy.vercel.app,http://localhost:3000

# Server Configuration
PORT=8080
```

### **Step 3: Get Backend URL**
- Railway will provide a URL like: `https://zen-therapy-backend.railway.app`

## 🎨 **Frontend Deployment (Vercel)**

### **Step 1: Prepare Frontend**
1. Go to [Vercel.com](https://vercel.com)
2. Click "New Project" → "Import Git Repository"
3. Select your repository
4. Set the following environment variables:

```env
REACT_APP_API_URL=https://zen-therapy-backend.railway.app
REACT_APP_NAME=Zen Therapy
```

### **Step 2: Deploy**
- Vercel will automatically deploy your frontend
- You'll get a URL like: `https://zen-therapy.vercel.app`

## 🔐 **Environment Variables Setup**

### **Railway Backend Variables:**
```env
DATABASE_URL=postgresql://username:password@host:port/database
HUGGINGFACE_API_TOKEN=hf_your_token_here
ALLOWED_ORIGINS=https://zen-therapy.vercel.app
PORT=8080
```

### **Vercel Frontend Variables:**
```env
REACT_APP_API_URL=https://zen-therapy-backend.railway.app
REACT_APP_NAME=Zen Therapy
```

## 🧪 **Testing Deployment**

### **1. Test Backend API**
```bash
curl https://zen-therapy-backend.railway.app/therapy/clients
```

### **2. Test Frontend**
- Visit your Vercel URL
- Test login functionality
- Test client management
- Test therapy chat

## 🔄 **Database Migration**

### **If using Railway PostgreSQL:**
1. The database will be automatically created
2. Tables will be created via JPA `ddl-auto=update`

### **If using existing MySQL:**
1. Export your local database:
```bash
mysqldump -u root -proot1234 therapy > therapy_backup.sql
```

2. Import to production database (if needed)

## 🚨 **Troubleshooting**

### **Common Issues:**

1. **CORS Errors**
   - Check `ALLOWED_ORIGINS` in Railway
   - Ensure frontend URL is included

2. **Database Connection**
   - Verify `DATABASE_URL` format
   - Check database credentials

3. **AI API Issues**
   - Verify Hugging Face token
   - Check API rate limits

4. **Build Failures**
   - Check Railway logs
   - Verify Java 17 compatibility

## 📊 **Monitoring**

### **Railway Dashboard:**
- Monitor backend performance
- Check database usage
- View deployment logs

### **Vercel Dashboard:**
- Monitor frontend performance
- Check build status
- View analytics

## 🔒 **Security Considerations**

1. **Environment Variables**
   - Never commit secrets to Git
   - Use Railway/Vercel environment variables

2. **CORS Configuration**
   - Only allow necessary origins
   - Use HTTPS in production

3. **Database Security**
   - Use strong passwords
   - Enable SSL connections

## 🎯 **Final Checklist**

- [ ] Backend deployed to Railway
- [ ] Frontend deployed to Vercel
- [ ] Database connected and working
- [ ] AI API configured
- [ ] CORS properly configured
- [ ] All features tested
- [ ] Environment variables set
- [ ] SSL certificates active

## 📞 **Support**

If you encounter issues:
1. Check Railway logs
2. Check Vercel build logs
3. Verify environment variables
4. Test API endpoints directly

---

**Your Zen Therapy app will be live at:**
- Frontend: `https://zen-therapy.vercel.app`
- Backend: `https://zen-therapy-backend.railway.app` 