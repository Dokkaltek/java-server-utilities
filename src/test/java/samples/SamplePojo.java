package samples;

/**
 * Sample POJO class to test reflection.
 */
public class SamplePojo extends SamplePojoParent {
    private String name;

    @SampleAnnotation
    private int age;

    public SamplePojo() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SampleAnnotation
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
