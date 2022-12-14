import React, { Component } from 'react';
import EmployeeService from '../services/EmployeeService';

class ListEmployeeComponent extends Component {
    
    constructor(props) {
        super(props)
        this.state = {
          employees :[]
        }
        this.addEmployee = this.addEmployee.bind(this);
        this.editEmployee= this.editEmployee.bind(this);
    }
    editEmployee(id){
        this.props.history.push(`/update-employee/${id}`);
    }

    componentDidMount(){
        EmployeeService.getEmployees().then((res)=>{
            this.setState({employees: res.data});
        });
    }

    addEmployee(){
            this.props.history.push('/add-employee');
    }

    render() {
        return (
            <div>
             <h2 className='text-center'>Employee List</h2>
             <div className='row'>
                <button type='button' className='btn btn-primary' onClick={this.addEmployee}>Add Employee</button>
             </div>
             <div className='row'>
                <table className='table table-striped table-bordered'>
                  <thead>
                    <tr>
                      <th> Employee first name </th>
                      <th> Employee last name </th>
                      <th> Employee email name </th>
                      <th> Actions </th>
                    </tr>
                  </thead>
                  <tbody>
                   {
                    this.state.employees.map(
                      employee =>
                      <tr key ={employee.id}>
                        <td>{employee.firstName}</td>
                        <td>{employee.lastName}</td>
                        <td>{employee.email}</td>
                        <td>
                            <button onClick={()=> this.editEmployee(employee.id)} className="btn btn-info">Update</button>
                        </td>
                      </tr>
                    )
                   }
                  </tbody>
                </table>
             </div>     
            </div>
        );
    }
}
export default ListEmployeeComponent;