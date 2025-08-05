#!/bin/bash

echo "🚀 Deploying Zen Therapy Frontend to Vercel..."

# Navigate to frontend directory
cd frontend

# Build the application
echo "📦 Building React application..."
npm run build

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo ""
    echo "🌐 Ready to deploy to Vercel!"
    echo ""
    echo "Next steps:"
    echo "1. Go to https://vercel.com"
    echo "2. Sign in with your GitHub account"
    echo "3. Click 'New Project'"
    echo "4. Import your repository: nicole-mathias/therapyApp"
    echo "5. Configure settings:"
    echo "   - Framework: Create React App"
    echo "   - Root Directory: frontend"
    echo "   - Build Command: npm run build"
    echo "   - Output Directory: build"
    echo ""
    echo "6. Add environment variable:"
    echo "   REACT_APP_API_URL=https://your-railway-backend-url.railway.app"
    echo ""
    echo "7. Click 'Deploy'"
    echo ""
    echo "🎉 Your app will be live at: https://zen-therapy-frontend.vercel.app"
else
    echo "❌ Build failed! Please check the errors above."
    exit 1
fi 