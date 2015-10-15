package stersectas.documentation;

/**
 * Constructors marked with this annotation are meant for hibernate, not for general use.
 * This is necessary so hibernate can fill objects from the database, as it first starts
 * out with an empty object created with the empty constructor, then with reflection fills
 * the variables.
 */
public @interface HibernateConstructor {

}
