import Settings_gradle.Group.*

rootProject.name = "-amm-java-9-91-2024"


student("sbrodov.andrey", G2_2) {
    lab(0)
}

student("sbrodova.daria", G9) {
    lab(0)
}

student("chzhan.ira", G91) {
    lab(0)
    lab(2)
    lab(1)
}

student("kireev.dmitrii", G91) {
    lab(0)
    lab(1)
    lab(2)
}

student("sbrodova.daria", G91) {
    lab(0)
}

student("bogdanov.nikita", G9) {
    lab(0)
    lab(1)
}

student("kurkina.tanya", G91) {
    lab(0)
    lab(1)
}

student("sergacheva.victoria", G91) {
    lab(0)
    lab(1)
    lab(2)
}

student("belozerov.alexei", G91) {
    lab(0)
    lab(1)
    lab(2)
}

student("gonnykh.alexander", G91) {
    lab(0)
    lab(1)
    lab(2)
}

student("simonov.ivan", G91) {
    lab(0)
    lab(1)
    lab(2)
    lab(3)
}

student("ulyanov.leonid", G91) {
    lab(0)
    lab(1)
    lab(2)
}

student("nelezin.oleg", G9) {
    lab(0)
    lab(1)
    lab(2)
    lab(3)
    lab(4)
}

student("golukovich.timofey", G9) {
    lab(0)
    lab(1)
    lab(2)
    lab(3)
}


student("bogatyirev.kirill", G9){
    lab(0)
    lab(1)
    lab(2)
 }


student("zayseva.anastasiya", G9) {
  lab(0)
}

student("korneeva.svetlana", G91) {
    lab(0)
    lab(1)
    lab(2)
}
student("abdrahmanova.evgeniya", G91) {
    lab(0)
}

student("barbashina.irina", G9) {
    lab(0)
    lab(1)
    lab(2)
    lab(3)
}

student("em.vera", G91) {
    lab(0)
    lab(1)
}

student("em.vera", G91) {
    lab(0)
    lab(1)
    lab(2)
}

student("dolzhenkov.dmitrii", G91) {
    lab(0)
    lab(1)
    lab(2)
}

student("popova.anastasia", G91) {
    lab(0)
    lab(1)
    lab(2)
}

student("gadzhiev.maksym", G9) {
    lab(0)
    lab(1)
    lab(2)
}

student("barkov.pavel", G9) {
    lab(0)
    lab(1)
    lab(2)
}

student("mokshin.nikita", G9) {
    lab(0)
}

student("anikandrov.andrey", G9) {
    lab(0)
    lab(1)
}

student("kanatnikov.maxim", G9) {
    lab(0)
    lab(1)
    lab(2)
}


student("garshin.maxim", G9) {
    lab(0)
    lab(1)
    lab(2)
}

student("jasser.daniel", G9) {
    lab(0)
}
student("jasser.daniel", G9) {
    lab(1)
}

student("gavrilov.mihail", G9) {
    lab(0)
}
student ("safonova.nastia", G9){
    lab(0)
    lab(1)
}
student("serova.polina", G9) {
    lab(0)
    lab(1)
    lab(2)
    lab(3)
}

student("shipilova.viktoria", G9) {
    lab(0)
    lab(1)
    lab(2)
    lab(3)
    lab(4)
}
student("kreydun.nicholas", G9) {
    lab(0)
    lab(1)
    lab(2)
    lab(3)
}

student("enokyan.gera", G91) {
    lab(0)
    lab(1)
    lab(2)
    lab(3)
    lab(4)
}

student("globuchik.dmitry", G9) {
    lab(0)
}


student("nikitina.elizaveta", G9) {
    lab(0)
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
