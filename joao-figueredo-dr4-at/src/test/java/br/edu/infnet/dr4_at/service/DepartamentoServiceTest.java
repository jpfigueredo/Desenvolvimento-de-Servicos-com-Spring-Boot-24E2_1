package br.edu.infnet.dr4_at.service;

import br.edu.infnet.dr4_at.model.Departamento;
import br.edu.infnet.dr4_at.repository.DepartamentoRepository;
import br.edu.infnet.dr4_at.service.impl.DepartamentoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartamentoServiceTest {

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private DepartamentoServiceImpl departamentoService;

    private Departamento departamento1;
    private Departamento departamento2;

    @BeforeEach
    void setUp() {
        departamento1 = new Departamento(1L, "Desenvolvimento", "São Paulo");
        departamento2 = new Departamento(2L, "Marketing", "Rio de Janeiro");
    }

    @Test
    @DisplayName("Deve retornar todos os departamentos")
    void testGetAllDepartamentos() {
        when(departamentoRepository.findAll()).thenReturn(Arrays.asList(departamento1, departamento2));
        List<Departamento> departamentos = departamentoService.getAll();
        assertNotNull(departamentos);
        assertFalse(departamentos.isEmpty());
        assertEquals(2, departamentos.size());
    }

    @Test
    @DisplayName("Deve retornar um departamento pelo ID")
    void testGetDepartamentoById() {
        Long departamentoId = 1L;
        when(departamentoRepository.findById(departamentoId)).thenReturn(Optional.of(departamento1));
        Optional<Departamento> departamento = departamentoService.getById(departamentoId);
        assertTrue(departamento.isPresent());
        assertEquals("Desenvolvimento", departamento.get().getNome());
    }

    @Test
    @DisplayName("Deve salvar um novo departamento")
    void testSaveDepartamento() {
        when(departamentoRepository.save(any(Departamento.class))).thenReturn(departamento1);
        Departamento savedDepartamento = departamentoService.save(departamento1);
        assertNotNull(savedDepartamento);
        assertEquals("Desenvolvimento", savedDepartamento.getNome());
    }

    @Test
    @DisplayName("Deve atualizar um departamento existente")
    void testUpdateDepartamento() {
        when(departamentoRepository.existsById(1L)).thenReturn(true);
        when(departamentoRepository.save(any(Departamento.class))).thenReturn(departamento1);
        Departamento updatedDepartamento = departamentoService.update(1L, departamento1);
        assertNotNull(updatedDepartamento);
        assertEquals("Desenvolvimento", updatedDepartamento.getNome());
    }

    @Test
    @DisplayName("Deve deletar um departamento pelo ID")
    void testDeleteDepartamento() {
        Long departamentoId = 3L;
        when(departamentoRepository.findById(departamentoId)).thenReturn(Optional.of(departamento1));
        doNothing().when(departamentoRepository).deleteById(departamentoId);
        departamentoService.delete(departamentoId);
        verify(departamentoRepository, times(1)).deleteById(departamentoId);
    }
}
