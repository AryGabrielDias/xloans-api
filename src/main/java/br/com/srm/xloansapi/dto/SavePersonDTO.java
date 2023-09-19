package br.com.srm.xloansapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavePersonDTO {

    private Long id;

    private String name;

    private String identification;

    private String birthDate;

}
