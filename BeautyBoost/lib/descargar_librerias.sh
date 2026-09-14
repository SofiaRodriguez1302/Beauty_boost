#!/bin/bash
# ============================================================================
# Beauty Boost - Descarga automática de conectores/librerías (.jar)
# ============================================================================
# Ejecuta este script DESDE la raíz del proyecto (donde está esta carpeta lib/)
# para descargar automáticamente, desde Maven Central, todos los .jar que el
# proyecto necesita y que NO vienen incluidos en el .zip (por su tamaño y
# licencias, no se distribuyen dentro del proyecto).
#
# Uso:
#   cd lib
#   bash descargar_librerias.sh
#
# Requisitos: tener 'curl' o 'wget' instalado.
# ============================================================================

set -e

BASE="https://repo.maven.apache.org/maven2"

declare -A JARS=(
  ["mysql-connector-j-8.4.0.jar"]="$BASE/com/mysql/mysql-connector-j/8.4.0/mysql-connector-j-8.4.0.jar"
  ["jakarta.servlet.jsp.jstl-3.0.1.jar"]="$BASE/org/glassfish/web/jakarta.servlet.jsp.jstl/3.0.1/jakarta.servlet.jsp.jstl-3.0.1.jar"
  ["jakarta.mail-api-2.1.3.jar"]="$BASE/jakarta/mail/jakarta.mail-api/2.1.3/jakarta.mail-api-2.1.3.jar"
  ["angus-mail-2.0.4.jar"]="$BASE/org/eclipse/angus/angus-mail/2.0.4/angus-mail-2.0.4.jar"
  ["jakarta.activation-api-2.1.3.jar"]="$BASE/jakarta/activation/jakarta.activation-api/2.1.3/jakarta.activation-api-2.1.3.jar"
  ["angus-activation-2.0.2.jar"]="$BASE/org/eclipse/angus/angus-activation/2.0.2/angus-activation-2.0.2.jar"
  ["jbcrypt-0.4.jar"]="$BASE/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar"
)

for nombre in "${!JARS[@]}"; do
  url="${JARS[$nombre]}"
  if [ -f "$nombre" ]; then
    echo "OK  (ya existe) $nombre"
    continue
  fi
  echo "Descargando $nombre ..."
  if command -v curl >/dev/null 2>&1; then
    curl -fSL -o "$nombre" "$url"
  elif command -v wget >/dev/null 2>&1; then
    wget -O "$nombre" "$url"
  else
    echo "ERROR: no se encontró 'curl' ni 'wget'. Instala uno de los dos o descarga manualmente: $url"
    exit 1
  fi
done

echo ""
echo "Listo. Archivos en $(pwd):"
ls -la *.jar
