import React from "react";

interface AppointmenItemProps{
    appointment:{
        id: string;
        name: string;
        reason: string;
        attorney: number;
        dateTime: Date | null;
    }
}

const SingleAppointment: React.FC<AppointmenItemProps> = ({appointment}) =>{

    return(
        <li >
            <div className="single-appointment">
                <div>
                    <span>Name: {appointment.name}</span>
                    <br />
                    <span>Reason: {appointment.reason}</span>
                    <br />
                    <span>Attorney: {appointment.attorney}</span>
                    <br />
                    <span>Date & Time: {appointment.dateTime ? appointment.dateTime.toLocaleString() : 'Not specified'}</span>
                </div>
            </div>
        </li>
    );
}

export default SingleAppointment;