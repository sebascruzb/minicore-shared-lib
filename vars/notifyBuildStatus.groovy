#!/usr/bin/env groovy
// ============================================================
//  SHARED LIBRARY — vars/notifyBuildStatus.groovy
//  Notificaciones reutilizables para el pipeline
// ============================================================

def call(String status, String message = '') {

    String color
    String emoji

    switch (status) {
        case 'STARTED':
            color = '#5D8FE8'; emoji = '🚀'; break
        case 'SUCCESS':
            color = '#36A64F'; emoji = '✅'; break
        case 'FAILURE':
            color = '#D40E0E'; emoji = '❌'; break
        case 'UNSTABLE':
            color = '#FFA500'; emoji = '⚠️'; break
        default:
            color = '#808080'; emoji = 'ℹ️'
    }

    String fullMsg = "${emoji} *[${env.APP_NAME ?: 'Jenkins'}]* ${status}\n" +
                     "Rama: `${env.BRANCH_NAME}` | Build: `#${env.BUILD_NUMBER}`\n" +
                     "Mensaje: ${message}\n" +
                     "URL: ${env.BUILD_URL}"

    echo "📢 Notificación [${status}]: ${message}"

    // Slack (requiere plugin Slack Notification)
    try {
        slackSend(
            color  : color,
            message: fullMsg,
            channel: '#minicore-ci'
        )
    } catch (Exception e) {
        echo "⚠️  Slack no configurado: ${e.message}"
    }
}
