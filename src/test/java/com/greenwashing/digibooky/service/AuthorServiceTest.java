    package com.greenwashing.digibooky.service;

    import com.greenwashing.digibooky.domain.Author;
    import com.greenwashing.digibooky.infrastructure.AuthorRepository;
    import com.greenwashing.digibooky.service.DTOs.AuthorInputDTO;
    import com.greenwashing.digibooky.service.DTOs.AuthorOutputDTO;
    import com.greenwashing.digibooky.service.mappers.AuthorMapper;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.junit.jupiter.MockitoExtension;

    import java.util.List;

    import static org.assertj.core.api.Assertions.assertThat;
    import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    public class AuthorServiceTest {

        @Mock
        private AuthorRepository authorRepository;

        @Mock
        private AuthorMapper authorMapper;

        @InjectMocks
        private AuthorService authorService;

        @Test
        void whenGetALlAuthors_thenReturnDTOList() {
            Author author1 = new Author("a", "b");
            Author author2 = new Author("c", "d");

            when(authorRepository.getAll()).thenReturn(List.of(author1, author2));
            when(authorMapper.authorToOutputDTO(author1)).thenReturn(new AuthorOutputDTO(1, "a", "b"));
            when(authorMapper.authorToOutputDTO(author2)).thenReturn(new AuthorOutputDTO(2, "c", "d"));

            List<AuthorOutputDTO> result = authorService.getAll();

            assertThat(result).hasSize(2);
            assertThat(result.get(0).getFirstName()).isEqualTo("a");
            assertThat(result.get(1).getLastName()).isEqualTo("d");
        }

        @Test
        void givenId_whenGetById_thenReturnDTO() {
            Author author = new Author("a", "b");

            when(authorRepository.getById(1)).thenReturn(author);
            when(authorMapper.authorToOutputDTO(author)).thenReturn(new AuthorOutputDTO(1, "a", "b"));

            AuthorOutputDTO result = authorService.getById(1);

            assertThat(result).isEqualTo(new AuthorOutputDTO(1, "a", "b"));
        }

        @Test
        void givenWrongId_whenGetById_thenReturnNull() {
            when(authorRepository.getById(1)).thenReturn(null);

            AuthorOutputDTO result = authorService.getById(1);

            assertThat(result).isNull();
        }

        @Test
        void givenInput_whenSave_thenReturnDTO() {
            AuthorInputDTO inputDTO = new AuthorInputDTO("a", "b");
            Author author = new Author("a", "b");
            AuthorOutputDTO authorOutputDTO = new AuthorOutputDTO(1, "a", "b");

            when(authorMapper.inputDTOToAuthor(inputDTO)).thenReturn(author);
            when(authorMapper.authorToOutputDTO(author)).thenReturn(authorOutputDTO);

            AuthorOutputDTO result = authorService.save(inputDTO);

            assertThat(result).isEqualTo(authorOutputDTO);
            verify(authorRepository).save(author);
        }

        // Need to check return type in service?
        @Test
        void givenValidId_whenDelete_thenDelete() {
            Author author = new Author("a", "b");
            long id = author.getId();

            when(authorRepository.getById(id)).thenReturn(author);

            authorService.delete(id);

            verify(authorRepository).delete(id);
        }

        @Test
        void givenInvalidId_whenDelete_thenReturnNull() {
            long id = 123;

            when(authorRepository.getById(id)).thenReturn(null);

            authorService.delete(id);

            verify(authorRepository, never()).delete(id);
        }
    }
