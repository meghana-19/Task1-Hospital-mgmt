package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.print("Enter Patient Name: ");
        String patient_name = scanner.next();
        System.out.print("Enter Patient Age: ");
        int patient_age = scanner.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender = scanner.next();

        try{
            String query = "INSERT INTO Patient(patient_name, patient_age, gender) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, patient_name);
            preparedStatement.setInt(2, patient_age);
            preparedStatement.setString(3, gender);
            
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                System.out.println("Patient Added Successfully!!");
            }else{
                System.out.println("Failed to add Patient!!");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatients(){
        String query = "select * from Patient";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+---------------+----------------+----------+");
            System.out.println("| Patient Name  |   Patient Age      | Gender   |");
            System.out.println("+---------------+----------------+----------+");
            while(resultSet.next()){
                String patient_name = resultSet.getString("patient_name"); 
                int patient_age = resultSet.getInt("patient_age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-18s    |    %-8s    |    %-10s   |\n", patient_name, patient_age, gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getPatientByName(String patient_name){
        String query = "SELECT * FROM Patient WHERE patient_name = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, patient_name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
