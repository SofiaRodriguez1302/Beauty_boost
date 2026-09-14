# Beauty Boost - descarga de todas las librerias necesarias
# Ejecutar este archivo desde PowerShell. Descarga los JAR directamente en esta carpeta.
$ErrorActionPreference = 'Stop'
$Base = 'https://repo.maven.apache.org/maven2'

$jars = @(
    @{ Name='mysql-connector-j-8.4.0.jar'; Url="$Base/com/mysql/mysql-connector-j/8.4.0/mysql-connector-j-8.4.0.jar" },
    @{ Name='jakarta.servlet.jsp.jstl-3.0.1.jar'; Url="$Base/org/glassfish/web/jakarta.servlet.jsp.jstl/3.0.1/jakarta.servlet.jsp.jstl-3.0.1.jar" },
    @{ Name='jakarta.mail-api-2.1.3.jar'; Url="$Base/jakarta/mail/jakarta.mail-api/2.1.3/jakarta.mail-api-2.1.3.jar" },
    @{ Name='angus-mail-2.0.4.jar'; Url="$Base/org/eclipse/angus/angus-mail/2.0.4/angus-mail-2.0.4.jar" },
    @{ Name='jakarta.activation-api-2.1.3.jar'; Url="$Base/jakarta/activation/jakarta.activation-api/2.1.3/jakarta.activation-api-2.1.3.jar" },
    @{ Name='angus-activation-2.0.2.jar'; Url="$Base/org/eclipse/angus/angus-activation/2.0.2/angus-activation-2.0.2.jar" },
    @{ Name='jbcrypt-0.4.jar'; Url="$Base/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar" }
)

$lib = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $lib

Write-Host ''
Write-Host '=== Beauty Boost - Librerias Java ===' -ForegroundColor Cyan
Write-Host "Destino: $lib"
Write-Host ''

foreach ($jar in $jars) {
    $target = Join-Path $lib $jar.Name
    if (Test-Path $target) {
        Write-Host "OK  $($jar.Name)" -ForegroundColor Green
        continue
    }

    Write-Host "Descargando $($jar.Name)..." -ForegroundColor Yellow
    try {
        Invoke-WebRequest -Uri $jar.Url -OutFile $target -UseBasicParsing
        if (-not (Test-Path $target) -or (Get-Item $target).Length -lt 1024) {
            throw "El archivo descargado parece incompleto."
        }
        Write-Host "OK  $($jar.Name)" -ForegroundColor Green
    }
    catch {
        if (Test-Path $target) { Remove-Item $target -Force -ErrorAction SilentlyContinue }
        Write-Host "ERROR $($jar.Name): $($_.Exception.Message)" -ForegroundColor Red
        exit 1
    }
}

Write-Host ''
Write-Host 'Todas las librerias fueron descargadas correctamente.' -ForegroundColor Green
Write-Host 'Ahora abre BeautyBoost en NetBeans y ejecuta Clean and Build.' -ForegroundColor Cyan
Write-Host ''
