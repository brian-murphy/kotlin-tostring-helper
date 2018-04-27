package annotations
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD,
        AnnotationTarget.PROPERTY, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ToString(val name: String = "")
