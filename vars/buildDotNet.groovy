#!/usr/bin/env groovy
// ============================================================
//  SHARED LIBRARY — vars/buildDotNet.groovy
//  Estrategia 2: Pipeline modular y reutilizable
//  Uso: buildDotNet(solutionFile: 'X.sln', configuration: 'Release', outputDir: 'publish')
// ============================================================

/**
 * Compila una solución .NET con los parámetros indicados.
 *
 * @param config Map con:
 *   - solutionFile  : ruta al .sln  (requerido)
 *   - configuration : Debug | Release (default: Release)
 *   - outputDir     : directorio de salida (default: publish)
 *   - extraArgs     : argumentos adicionales para /usr/local/bin/dotnet build (default: '')
 */
def call(Map config = [:]) {

    String solutionFile  = config.get('solutionFile',  'MiniCore.sln')
    String configuration = config.get('configuration', 'Release')
    String outputDir     = config.get('outputDir',     'publish')
    String extraArgs     = config.get('extraArgs',     '')

    echo "buildDotNet — Solución: ${solutionFile} | Config: ${configuration} | Output: ${outputDir}"

    sh """
    echo "--- Verificando versión de .NET ---"
    /usr/local/bin/dotnet --version

    echo "--- Compilando ${solutionFile} ---"

    /usr/local/bin/dotnet build ${solutionFile} \
        --configuration ${configuration} \
        --no-restore \
        --output ${outputDir} \
        --verbosity minimal \
        ${extraArgs} \
        2>&1 | tee build-output.log

    echo "--- Resumen del Build ---"

    grep -E "(Warning|Error|warning|error)" build-output.log \
        | grep -v "0 Error(s)" \
        | grep -v "0 Warning(s)" \
        | head -20 || true
"""

    // Archivar log del build
    archiveArtifacts artifacts: 'build-output.log', allowEmptyArchive: true

    echo "✅  buildDotNet completado exitosamente"
}
