import axios from "axios"

const appointmentApi = {

    getAppointments: () => axios.get('http://localhost:8080/appointment').then(response => response.data),
    createAppointment: (newAppointment: any) => axios.post('http://localhost:8080/appointment', newAppointment),
    deleteAccount: (appointmentId: string) => axios.delete(`http://localhost:8080/appointment/${appointmentId}`)
    

}
export default appointmentApi