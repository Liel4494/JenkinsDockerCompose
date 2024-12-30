node("jenkins-master"){
    stage("Clone Curl Repository"){
        cleanWs()
        
        checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/curl/curl.git']])   
        
        stash name: 'curl-repo'
    }    
}

node("jenkins-slave"){
    stage("Build Curl"){
        unstash 'curl-repo'
        sh returnStdout: true, script: 'autoreconf -fi'
        sh returnStdout: true, script: './configure --with-openssl'
        sh returnStdout: true, script: 'make'
        sh returnStdout: true, script: 'make install'        
        sh returnStdout: true, script: 'ls -la'
    }    
}

