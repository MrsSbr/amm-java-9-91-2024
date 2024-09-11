import Settings_gradle.Group.*

rootProject.name="-amm-java-9-91-2024"





// DSL для подпроектов [Не трогать]
includeProject("template-lab0", file("template/lab0"))

enum class Group {G9, G91}

fun student(name: String, group: Group, block: LabsAppender.() -> Unit) {
    LabsAppender(name, group).apply(block)
}

class LabsAppender(private val student: String, private val group: Group) {
    fun lab(labNum: Int) {
        val path = file("app/$group/$student/lab$labNum")
        val title = "$student-lab$labNum"
        path.mkdirs()
        includeProject(
            title = title,
            path = path
        )
    }
}

fun includeProject(title: String, path: File) {
    include(title)
    project(":$title").apply {
        this.projectDir = file(path)
        name = title
    }
}