stage("Clone Curl Repository"){
    node("jenkins-master"){
        cleanWs()
        
        checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/curl/curl.git']])   
        
        stash name: 'curl-repo'
    }    
}

stage("Build Curl"){
    node("jenkins-slave"){
        unstash 'curl-repo'
        sh returnStdout: true, script: 'ls -l'
        
    }    
}
