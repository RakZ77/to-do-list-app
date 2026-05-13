package kh.edu.rupp.to_dolistapp.models

class Task // Optional: set default values for other fields
// default white
    (var title: String?) {
    // Getters and Setters
    var id: Int = 0
    var name: String? = ""
    var progress: Int = 0
    var color: String? = "#FFFFFF"
}