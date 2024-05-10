package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

//import com.basic.model.Appointment;

public class HospitalManagementSystem {
    private static final String url = "jdbc:postgresql://localhost:5432/hospital";
    private static final String username = "postgres";
    private static final String password = "12345";
    //private static Appointment appointments = new Appointment();


    public static void main(String[] args) {
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. view appointment");
                System.out.println("6. Exit");
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();

                switch(choice){
                    case 1:
                        // Add Patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        // View Patient
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        // View Doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        // Book Appointment
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        viewAppointments(connection);
                        System.out.println();
                        break;
                    case 6:
                        System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                        return;
                    default:
                        System.out.println("Enter valid choice!!!");
                        break;
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner){
        System.out.print("Enter Patient Name: ");
        String patient_name = scanner.next();
        System.out.print("Enter Doctor Id: ");
        int doctor_id = scanner.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointment_date_str = scanner.next();
        // Convert appointment date string to java.sql.Date
        java.sql.Date appointment_date = java.sql.Date.valueOf(appointment_date_str);
        
        if(patient.getPatientByName(patient_name) && doctor.getDoctorById(doctor_id)){
            if(checkDoctorAvailability(doctor_id, appointment_date, connection)){
                String appointmentQuery = "INSERT INTO appointments(patient_name, doctor_id, appointment_date) VALUES(?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setString(1, patient_name);
                    preparedStatement.setInt(2, doctor_id);
                    preparedStatement.setDate(3, appointment_date);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected>0){
                        System.out.println("Appointment Booked!");
                    }else{
                        System.out.println("Failed to Book Appointment!");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor not available on this date!!");
            }
        }else{
            System.out.println("Either doctor or patient doesn't exist!!!");
        }
    }

    public static boolean checkDoctorAvailability(int doctor_id, Date appointment_date, Connection connection){
        String query = "SELECT COUNT(*) FROM Appointments WHERE doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctor_id);
            preparedStatement.setDate(2,appointment_date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    private static void viewAppointments(Connection connection) {
        System.out.println("Appointments: ");
        System.out.println("+--------------+------------+----------------------+");
        System.out.println("| Patient Name | Doctor ID  | Appointment Date     |");
        System.out.println("+---------------+------------+----------------------+");

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Appointments");

            while (resultSet.next()) {
                String patient_name = resultSet.getString("patient_name");
                int doctor_id = resultSet.getInt("doctor_id");
                Date appointment_date= resultSet.getDate("appointment_date");
                System.out.printf("| %-10s| %-10d | %-20s |\n", patient_name, doctor_id, appointment_date);
                System.out.println("+------------+------------+----------------------+");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
