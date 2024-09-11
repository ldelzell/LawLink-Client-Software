import React, { useState } from 'react';
import '../styles/Pages-CSS/CreateAppointment.css';
import InputDataCreateAppointment from '../components/Appointment/InputDataCreateAppointment';
import appointmentApi from '../api/appointmentApi';

interface Appointment {
  name: string;
  reason: string;
  attorney: number;
  dateTime: Date | null;
}

const CreateAppointmentPage: React.FC = () => {
  const addAppointment = async (name: string, reason: string, attorney: number, dateTime: Date | null): Promise<void> => {
    const newAppointment: Appointment = {
      name,
      reason,
      attorney,
      dateTime,
    };
    console.log(newAppointment);

    try {
      await appointmentApi.createAppointment(newAppointment);
      console.log("Appointment created successfully");

    } catch (error) {
      console.error('Error creating appointment:', error);
    }
  };

  return (
    <div className="create-appointment-container">
      <div className="create-appointment-container-inner">
        <h1 className="title">Create Appointment Page</h1>
        <InputDataCreateAppointment onSubmit={addAppointment} />
      </div>
    </div>
  );
}

export default CreateAppointmentPage;
