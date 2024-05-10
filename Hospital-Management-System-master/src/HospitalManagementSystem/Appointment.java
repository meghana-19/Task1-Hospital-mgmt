package HospitalManagementSystem;

import java.time.LocalDate;

class Appointment {
    private int patient_name;
    private int doctor_id;
    private LocalDate appointment_date;

    

    public Appointment(int patient_name, int doctor_id, LocalDate appointment_date) {
        super();
        this.patient_name = patient_name;
        this.doctor_id = doctor_id;
        this.appointment_date = appointment_date;
    }

    public int getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(int patient_name) {
        this.patient_name = patient_name;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public LocalDate getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(LocalDate appointment_date) {
        this.appointment_date = appointment_date;
    }

    @Override
    public String toString() {
        return "Appointment [patient_name=" + patient_name + ", doctor_id=" + doctor_id + ", appointment_date=" + appointment_date + "]";
    }

    public Appointment() {
        super();
        // TODO Auto-generated constructor stub
    }

    
}