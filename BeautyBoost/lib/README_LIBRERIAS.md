# Librerías de BeautyBoost

El proyecto está configurado en NetBeans para utilizar estas 7 librerías locales dentro de `lib/`:

1. mysql-connector-j-8.4.0.jar
2. jakarta.servlet.jsp.jstl-3.0.1.jar
3. jakarta.mail-api-2.1.3.jar
4. angus-mail-2.0.4.jar
5. jakarta.activation-api-2.1.3.jar
6. angus-activation-2.0.2.jar
7. jbcrypt-0.4.jar

Las seis primeras son las dependencias indicadas para el proyecto. `jbcrypt-0.4.jar` es adicional porque el código de autenticación utiliza BCrypt para proteger las contraseñas.

## Descargar automáticamente en Windows

Abre PowerShell dentro de esta carpeta y ejecuta:

```powershell
Set-ExecutionPolicy -Scope Process Bypass
.\descargar_librerias.ps1
```

También puedes ejecutar `descargar_librerias.bat`.

> El ZIP de este proyecto incluye la configuración y los scripts, pero en este entorno no fue posible incorporar físicamente los JAR porque no hay acceso de red para descargarlos. No se debe considerar el proyecto completamente autocontenido hasta que los 7 archivos `.jar` aparezcan dentro de `lib/`.
