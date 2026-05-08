#!/usr/bin/env groovy
// ============================================================
//  SHARED LIBRARY — vars/getBuildConfig.groovy
//  Retorna la configuración de build según la rama
// ============================================================

def call(String branchName) {
    if (branchName == 'main') {
        return 'Release'
    } else if (branchName == 'develop') {
        return 'Release'
    } else {
        return 'Debug'
    }
}
