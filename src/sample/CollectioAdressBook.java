package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 13.07.17.
 */
public class CollectioAdressBook implements AdressBook {

    private ObservableList<Person> personList = FXCollections.observableArrayList();

    @Override
    public void add(Person person) {
        personList.add(person);
    }

    @Override
    public void update(Person person) {

    }

    @Override
    public void delete(Person person) {
        personList.remove(person);
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }

    public void fillTestData(){
        personList.add(new Person("q","1"));
        personList.add(new Person("w","2"));
        personList.add(new Person("e","3"));
        personList.add(new Person("r","4"));
        personList.add(new Person("t","5"));
        personList.add(new Person("y","6"));
        personList.add(new Person("u","7"));
        personList.add(new Person("i","8"));

    }
}
