import Settings_gradle.Group.*

rootProject.name = "-amm-java-9-91-2024"


student("sbrodov.andrey", G2_2) {
    lab(0)
}

student("sbrodova.daria", G9) {
    lab(0)
}

student("kireev.dmitrii", G91) {
    lab(0)
}

student("sbrodova.daria", G91) {
    lab(0)
}


student("belozerov.alexei", G91) {
    lab(0)
}

student("gonnykh.alexander", G91) {
    lab(0)
}

student("nelezin.oleg", G9) {
    lab(0)
}

student("golukovich.timofey", G9) {
    lab(0)
}

student("bogatyirev.kirill", G9) {
    lab(1)
}

// DSL для подпроектов [Не трогать]
includeProject("template-lab0", file("template/lab0"))

enum class Group { G9, G91, G2_2 }

fun student(name: String, group: Group, block: LabsAppender.() -> Unit = {}) {
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