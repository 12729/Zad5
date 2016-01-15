package com.piatkowskilukasz;

/**
 * Created by LouL on 15.01.2016.
 */
import com.piatkowskilukasz.database.*;
public class Application {

    public static void main(String args[]) {
        CreatingDemonstration();
        System.out.println("Finished! See results on the HSQL database.");
    }

    private static void CreatingDemonstration() {

        DatabaseContext context = new DatabaseContext(new HsqlDatabase());

        // Declare permission
        context.permissions().add(new Permission("Add"));
        context.permissions().add(new Permission("Edit"));
        context.permissions().add(new Permission("Delete"));
        context.permissions().add(new Permission("ReadOnly"));
        context.permissions().add(new Permission("Restore"));

        // Declare roles
        context.roles().add(new Role("Admin"));
        context.roles().add(new Role("Teacher"));
        context.roles().add(new Role("Student"));

        // Insert data
        context.saveChanges();
    }
}
