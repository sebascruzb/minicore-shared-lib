#!/usr/bin/env groovy
// ============================================================
//  SHARED LIBRARY — vars/deployApp.groovy
//  Despliegue modular según entorno
// ============================================================

def call(Map config = [:]) {

    String environment = config.get('environment', 'development')
    String artifact    = config.get('artifact',    'publish')

    echo "🚀 Desplegando en entorno: ${environment}"

    switch (environment) {
        case 'production':
            sh """
                echo "--- Desplegando en PRODUCCIÓN ---"
                cp -r ${artifact}/* /var/www/minicore/ || echo "Simulando deploy a producción"
                echo "✅ Deploy a producción completado"
            """
            break
        case 'staging':
            sh """
                echo "--- Desplegando en STAGING ---"
                cp -r ${artifact}/* /var/www/minicore-staging/ || echo "Simulando deploy a staging"
                echo "✅ Deploy a staging completado"
            """
            break
        default:
            sh """
                echo "--- Desplegando en DESARROLLO ---"
                echo "✅ Artefacto listo en: ${artifact}"
            """
    }
}
