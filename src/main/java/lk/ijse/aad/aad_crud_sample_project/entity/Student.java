/*
 * Author  : Mr.electrix
 * Project : AAD_CRUD_Sample_Project
 * Date    : 7/6/24

 */

package lk.ijse.aad.aad_crud_sample_project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    private String id;
    private String name;
    private String email;
    private String city;
    private String level;
}
