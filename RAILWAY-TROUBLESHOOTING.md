# 🚂 Railway Deployment Troubleshooting

## ✅ **Fixed Issues**

### **DataSource Bean Error**
**Problem**: `Parameter 0 of method dataSourceScriptDatabaseInitializer required a bean of type 'javax.sql.DataSource' that could not be found`

**Solution**: 
- ✅ Removed problematic DataSource bean override
- ✅ Simplified DatabaseConfig to only handle dialect detection
- ✅ Let Spring Boot handle DataSource creation automatically

## 🔧 **Current Configuration**

### **DatabaseConfig.java**
```java
@PostConstruct
public void configureDatabase() {
    if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
        // PostgreSQL configuration
        System.setProperty("spring.jpa.properties.hibernate.dialect", 
            "org.hibernate.dialect.PostgreSQLDialect");
        System.setProperty("spring.datasource.driver-class-name", 
            "org.postgresql.Driver");
    } else {
        // MySQL configuration (default)
        System.setProperty("spring.jpa.properties.hibernate.dialect", 
            "org.hibernate.dialect.MySQLDialect");
        System.setProperty("spring.datasource.driver-class-name", 
            "com.mysql.cj.jdbc.Driver");
    }
}
```

## 🚀 **Deploy Again**

1. **Railway will automatically redeploy** with the new code
2. **The DataSource error should be resolved**
3. **Database connection should work properly**

## 📋 **Environment Variables for Railway**

Make sure these are set in Railway:

```
DATABASE_URL=postgresql://username:password@host:port/database
HUGGINGFACE_API_TOKEN=hf_your_token_here
ALLOWED_ORIGINS=https://zen-therapy.vercel.app
```

## 🐛 **If Still Having Issues**

### **Check Railway Logs**
1. Go to Railway dashboard
2. Click on your service
3. Check "Logs" tab for errors

### **Common Issues**
1. **Database not connected**: Add PostgreSQL database in Railway
2. **Environment variables missing**: Set them in Railway settings
3. **Port conflicts**: Railway handles this automatically

### **Manual Redeploy**
1. In Railway dashboard
2. Go to "Settings" → "Deploy"
3. Click "Redeploy"

## ✅ **Expected Behavior**

After this fix:
- ✅ No DataSource bean errors
- ✅ Automatic database detection (PostgreSQL/MySQL)
- ✅ Proper connection to Railway PostgreSQL
- ✅ Application starts successfully

## 📞 **Next Steps**

1. **Wait for Railway to redeploy** (automatic)
2. **Check the logs** for successful startup
3. **Test the API endpoints**
4. **Deploy frontend to Vercel** 