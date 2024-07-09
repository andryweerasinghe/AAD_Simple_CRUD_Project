/*
 * Author  : Mr.electrix
 * Project : AAD_CRUD_Sample_Project
 * Date    : 7/6/24

 */

package lk.ijse.aad.aad_crud_sample_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO implements Serializable {
    private String id;
    private String name;
    private String email;
    private String city;
    private String level;
}
