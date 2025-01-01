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
        
        echo "\n## Building Curl ##"
        sh returnStdout: true, script: 'autoreconf -fi'
        sh returnStdout: true, script: './configure --with-openssl'
        sh returnStdout: true, script: 'make'
        sh returnStdout: true, script: 'make install'        
        dir("src"){
            def curlOutput = sh(returnStdout: true, script: './curl --version')
            echo "Curl Output:\n${curlOutput}"            
        }
        
    }
    
    stage("Test Curl"){
        echo "\n## Running Tests ##"
        sh returnStdout: true, script: 'make test'
    }      
    
    stage("Archive Curl"){
        echo "\n## Archiving Curl Binary ##"
        archiveArtifacts artifacts: 'src/curl', followSymlinks: false
    }    
}


