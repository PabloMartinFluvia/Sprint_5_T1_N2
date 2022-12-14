package com.martinfluviapablo.s5t1n2.model.services;

import com.martinfluviapablo.s5t1n2.model.dto.FlorDto;
import java.util.List;

public interface FlorCrudService {

    public List<FlorDto> getAll();

    public FlorDto getOne(Integer id);

    public void deleteOne(Integer id);

    public FlorDto addOne(FlorDto dto);

    public FlorDto replaceOne(FlorDto dto);
}
