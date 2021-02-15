package nl.bkwi.twi.gateway;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import graphql.GraphQL;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationValidationParameters;
import graphql.language.Node;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.validation.ValidationError;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.ResourceResolver;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import nl.bkwi.twi.gateway.fetcher.*;

@Slf4j
@Factory
public class GraphQLFactory {

  @Bean
  @Singleton
  @Inject
  // too many parameters for now ignored will change once we build graphql endpoint per information-service
  public GraphQL graphQL(ResourceResolver resourceResolver,
      DuoDataFetcher duoDataFetcher,
      DuoStudiefinancieringDataFetcher duoStudiefinancieringDataFetcher,
      BrpGSDDataFetcher brpVraagDataFetcher,
      BijstandsregelingenDataFetcher bijstandsregelingenDataFetcher,
      SvbDataFetcher svbDataFetcher,
      UwvWerknemersverzekeringenDataFetcher uwvWerknemersverzekeringenDataFetcher,
      ReintegratieDataFetcher reintegratieDataFetcher,
      KadasterDataFetcher kadasterDataFetcher,
      UwvInkomstenDataFetcher uwvInkomstenDataFetcher,
      RdwDossierGsdDataFetcher rdwDossierGsdDataFetcher,
      BrpDossierPersoonBewonersVraagDataFetcher brpDossierPersoonsBewonersvraagDataFetcher) {


    log.info("Preparing graphQL bean");
    SchemaParser schemaParser = new SchemaParser();
    SchemaGenerator schemaGenerator = new SchemaGenerator();

    // Parse the schema.
    TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
    Optional<InputStream> stream = resourceResolver
        .getResourceAsStream("classpath:bind-generator-graphql/schema.graphqls");
    stream.ifPresent(theStream ->
        typeRegistry
            .merge(schemaParser.parse(new BufferedReader(new InputStreamReader(theStream)))));

    // Create the runtime wiring.
    RuntimeWiring runtimeWiring =
        RuntimeWiring.newRuntimeWiring()
            .type(newTypeWiring("Query").dataFetcher("info", fetcher -> new Object()))
            .type(
                newTypeWiring("InfoResponse")
                    .dataFetcher("BRPDossierPersoonBewonersvraagv0200AanvraagPersoon", brpDossierPersoonsBewonersvraagDataFetcher)
                    .dataFetcher("RDWDossierGSDv0200VoertuigbezitInfoPersoon", rdwDossierGsdDataFetcher)
                    .dataFetcher("DUODossierPersoonv0600DUOPersoonsInfo", duoDataFetcher)
                    .dataFetcher("DUODossierStudiefinancieringv0300DUOStudiefinancieringInfo", duoStudiefinancieringDataFetcher)
                    .dataFetcher("BRPDossierPersoonGSDv0200AanvraagPersoon", brpVraagDataFetcher)
                    .dataFetcher("Bijstandsregelingenv0500BijstandsregelingenInfo", bijstandsregelingenDataFetcher)
                    .dataFetcher("SVBDossierPersoonGSDv0200SVBPersoonsInfo", svbDataFetcher)
                    .dataFetcher("UWVDossierWerknemersverzekeringenv0200UWVPersoonsWvInfo", uwvWerknemersverzekeringenDataFetcher)
                    .dataFetcher("GSDDossierReintegratiev0200GSDReintegratieInfo", reintegratieDataFetcher)
                    .dataFetcher("KadasterDossierGSDv0300PersoonsInfo", kadasterDataFetcher)
                    .dataFetcher("UWVDossierInkomstenv0700UWVPersoonsIkvInfo", uwvInkomstenDataFetcher))
            .scalar(
                ExtendedScalars.newAliasedScalar("Long")
                    .aliasedScalar(ExtendedScalars.GraphQLLong)
                    .build())
            .build();

    // Create the executable schema.
    GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

    // Return the GraphQL bean.
    return GraphQL
      .newGraphQL(graphQLSchema)
      // Voeg instrumentatie toe zodat een graphql request, (de document), gecontroleerd kan worden.
      .instrumentation(new OnzeBkwiValidationInstrumention())
      .build();
  }

  private class OnzeBkwiValidationInstrumention extends SimpleInstrumentation {

    @Override
    public InstrumentationContext<List<ValidationError>> beginValidation(InstrumentationValidationParameters parameters) {
      var document = parameters.getDocument();
      // Code om al die velden uit te zetten.
      List<String> fieldList = new ArrayList<>();
      for (Node child : document.getChildren()) {
        // Recursive: Expand child names to a single list
      }

      // Do voorwaardeleveringcheck met de fieldList lijst.

      // Als voorwaarde check goed gaat:
      boolean leveringvoorwaardeCheckIsOk = false;
      if (leveringvoorwaardeCheckIsOk) {
        return new SimpleInstrumentationContext();
      } else {
        var c = new SimpleInstrumentationContext<List<ValidationError>>();

        c.onCompleted(List.of(ValidationError.newValidationError().build()), new NotAllowedToAccessException());
        return c;
      }

    }


  }
}
