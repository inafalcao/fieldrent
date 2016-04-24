package br.com.fieldrent.controller;

import br.com.fieldrent.model.Field;
import br.com.fieldrent.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by inafalcao on 1/18/16.
 */

@RestController
public class FieldController {

    @Autowired
    private FieldRepository fieldRepository;

    @RequestMapping(value = "/fields", method = RequestMethod.GET)
    public List<Field> list() {
        return fieldRepository.findAll();
    }

    @RequestMapping(value="/field/{id}", method = RequestMethod.GET)
    public Field getOne(@PathVariable("id") Long id) {
        return fieldRepository.findOne(id);
    }

    @RequestMapping(value = "/field", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody  Field field) {
        fieldRepository.save(field);
    }

    @RequestMapping(value = "/field/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody Field field) {
        Field existingField = fieldRepository.findOne(id);
        field.setId(existingField.getId());
        existingField = field;
        fieldRepository.save(existingField);
    }

    @RequestMapping(value = "/field/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        fieldRepository.delete(id);
    }

}
