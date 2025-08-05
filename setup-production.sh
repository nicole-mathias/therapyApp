#!/bin/bash

echo "🚀 Zen Therapy - Production Setup"
echo "=================================="

# Check if git is initialized
if [ ! -d ".git" ]; then
    echo "❌ Git repository not found. Please initialize git first:"
    echo "   git init"
    echo "   git add ."
    echo "   git commit -m 'Initial commit'"
    exit 1
fi

# Check if all files are committed
if [ -n "$(git status --porcelain)" ]; then
    echo "⚠️  You have uncommitted changes. Please commit them first:"
    echo "   git add ."
    echo "   git commit -m 'Production ready'"
    exit 1
fi

echo "✅ Code is ready for deployment"
echo ""
echo "📋 Next Steps:"
echo "1. Push to GitHub:"
echo "   git push origin main"
echo ""
echo "2. Deploy Backend (Railway):"
echo "   - Go to https://railway.app"
echo "   - Create new project"
echo "   - Deploy from GitHub repo"
echo "   - Add PostgreSQL database"
echo "   - Set environment variables"
echo ""
echo "3. Deploy Frontend (Vercel):"
echo "   - Go to https://vercel.com"
echo "   - Import GitHub repository"
echo "   - Set environment variables"
echo ""
echo "4. Configure Environment Variables:"
echo ""
echo "   Railway Backend:"
echo "   - DATABASE_URL=your_railway_postgresql_url"
echo "   - HUGGINGFACE_API_TOKEN=your_hf_token"
echo "   - ALLOWED_ORIGINS=https://zen-therapy.vercel.app"
echo ""
echo "   Vercel Frontend:"
echo "   - REACT_APP_API_URL=https://zen-therapy-backend.railway.app"
echo "   - REACT_APP_NAME=Zen Therapy"
echo ""
echo "📖 See DEPLOYMENT.md for detailed instructions"
echo ""
echo "🎯 Your app will be live at:"
echo "   Frontend: https://zen-therapy.vercel.app"
echo "   Backend: https://zen-therapy-backend.railway.app" 