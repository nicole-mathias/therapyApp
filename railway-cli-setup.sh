#!/bin/bash

echo "🚂 Railway CLI Setup for PostgreSQL"
echo "===================================="
echo ""

echo "📋 Step 1: Install Railway CLI"
echo "Run this command:"
echo "npm install -g @railway/cli"
echo ""

echo "📋 Step 2: Login to Railway"
echo "Run this command:"
echo "railway login"
echo ""

echo "📋 Step 3: Link to your project"
echo "Run this command:"
echo "railway link"
echo ""

echo "📋 Step 4: Add PostgreSQL service"
echo "Run this command:"
echo "railway service add postgresql"
echo ""

echo "📋 Step 5: Get your connection string"
echo "Run this command:"
echo "railway variables"
echo ""

echo "📋 Step 6: Set environment variables"
echo "Run these commands (replace with your actual values):"
echo ""
echo "railway variables set DATABASE_URL=\"your_postgresql_connection_string\""
echo "railway variables set DB_DRIVER=\"org.postgresql.Driver\""
echo "railway variables set HIBERNATE_DIALECT=\"org.hibernate.dialect.PostgreSQLDialect\""
echo "railway variables set HUGGINGFACE_API_TOKEN=\"hf_your_token_here\""
echo "railway variables set ALLOWED_ORIGINS=\"https://zen-therapy-frontend.vercel.app\""
echo ""

echo "📋 Step 7: Redeploy your backend"
echo "Run this command:"
echo "railway up"
echo ""

echo "✅ Your backend should now work with PostgreSQL!" 