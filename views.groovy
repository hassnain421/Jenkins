listFolderNames().each { folderName ->
    def folder = new File(workspace + "/" + folderName)
    if (folder.isDirectory()) {
        def viewName = folderName
        views {
            name(viewName)
            listStatusFilter('Enabled')
            filterBuildQueue()
            filterExecutors()
            recurse(false)
            includeRegex('.*')
            columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        folder.listFiles().each { file ->
            if (file.isFile() && file.name.endsWith('.groovy')) {
                def jobName = file.name.replaceAll('.groovy', '')
                def jobConfig = new File(folder, file.name).text
                job(viewName + '/' + jobName) {
                    configure(jobConfig)
                }
            }
        }
    }
}

def listFolderNames() {
    def list = []
    new File(pwd()).eachFile { file ->
        if (file.isDirectory()) {
            list.add(file.getName())
        }
    }
    return list
}
